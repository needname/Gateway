#include <wiringPi.h>
#include <stdio.h>
#include <stdlib.h>	
#include <stdint.h>
#include <time.h>
#include <arpa/inet.h>		// for sockadd_in and inet_addr()
#include <sys/socket.h>		// for socket(), connect(), sendto(), and recvfrom()
#include <string.h>

#define ECHOMAX 255		// longest string to echo
#define MAXTIMINGS 85
#define DHTPIN 7
int dht11_dat[5]={0,0,0,0,0};

void read_dht11_dat(float* temperature, float* humidity, int* _time){
	uint8_t laststate = HIGH;
	uint8_t counter = 0;
	uint8_t j = 0, i;
	float f;

	dht11_dat[0] = dht11_dat[1] = dht11_dat[2] = dht11_dat[3] = dht11_dat[4] = 0;

	pinMode(DHTPIN, OUTPUT);
	digitalWrite(DHTPIN, LOW);
	delay(18);
	digitalWrite(DHTPIN, HIGH);
	delayMicroseconds(40);
	pinMode(DHTPIN, INPUT);

	for(i = 0; i < MAXTIMINGS; ++i){
		counter = 0;
		while(digitalRead(DHTPIN) == laststate){
			counter++;
			delayMicroseconds(1);
			if(counter == 255){
				break;
			}
		}
		laststate = digitalRead(DHTPIN);
		if(counter == 255)
			break;
	
		if((i >= 4) && (i%2 == 0)){
			dht11_dat[j / 8] <<= 1;
			if(counter > 16)
				dht11_dat[j/8] |= 1;
			++j;
		}
	}

	if((j >= 40) && (dht11_dat[4] == ((dht11_dat[0] + dht11_dat[1] + dht11_dat[2] + dht11_dat[3]) & 0xFF))){
		f = dht11_dat[2] * 9./5. + 32;
		*temperature = dht11_dat[2] + dht11_dat[3] / 10.0;
		*humidity = dht11_dat[0] + dht11_dat[1] / 10.0;
	} else {
		*temperature = -1;
		*humidity = -1;
	}
	delay(*_time);
}

void DieWithError(char *errorMessage);

int main(int argc, char *argv[]){
	
	char* id = "sensor1";
	printf("Raspberry Pi wiringPi DHT11 Temperature test program\n");

	if(wiringPiSetup() == -1)
		exit(1);

	int sock;

	struct sockaddr_in echoServAddr;
	struct sockaddr_in fromAddr;
	unsigned short echoServPort = 8080;
	unsigned int fromSize;

	char *servIP;
	char echoString[ECHOMAX];
	char now[255];

	char echoBuffer[ECHOMAX+1];

	int echoStringLen;
	int respStringLen;

	int _time = 0;

	if(argc != 3){
		fprintf(stderr, "Usage: %s <time (ms), time >= 1000>\n",argv[0]);
		exit(1);
	}

	_time = atoi(argv[1]);
	if(_time < 1000){
		fprintf(stderr,"Sorry, the time of taking sample of  DHT11 is at least 1 time/second\n");
		exit(1);
	}

	servIP = argv[2];
	
	float temp, humd;
	temp = humd = 0;

	if((sock = socket(PF_INET, SOCK_DGRAM, IPPROTO_UDP)) < 0){
		fprintf(stderr,"socket() failed");
		exit(1);
	}

	memset(&echoServAddr, 0, sizeof(echoServAddr));
	echoServAddr.sin_addr.s_addr = inet_addr(servIP);
	echoServAddr.sin_port = htons(echoServPort);
	

	time_t rawtime;
	struct tm * timeinfo;

	while(1){
		read_dht11_dat(&temp, &humd, &_time);
		time (&rawtime);
		timeinfo = localtime (&rawtime);

		sprintf(now, "%d %d %d %d:%d:%d",timeinfo->tm_mday, timeinfo->tm_mon + 1, timeinfo->tm_year + 1900, timeinfo->tm_hour+7, timeinfo->tm_min, timeinfo->tm_sec);

		sprintf(echoString, "%s %s %.1f %.1f", id, now, temp, humd);
		printf("%s\n",echoString);

		echoStringLen = strlen(echoString);

		sendto(sock, echoString, echoStringLen, 0, (struct sockaddr *)&echoServAddr, sizeof(echoServAddr));
	}

	close(socket);
	return 0;
}

