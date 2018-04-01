/*import javax.swing.text.html.parser.Parser;
import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;

public class FullInformationRecord {
    private String id;
    private float temperature;
    private float humidity;
    private Date timestamp;

    FullInformationRecord(String record) {
        Enumeration tokens = Parser.parse(record);
        Dictionary<Token.Type, List<Token>> dict = Common.enum2dict(tokens);
        id = dict.get(Token.Type.Identifier).get(0).toString();
        temperature = dict.get(Token.Type.Temperature).get(0).toFloat();
        humidity = dict.get(Token.Type.Humidity).get(0).toFloat();
        timestamp = dict.get(Token.Type.TimeStamp).get(0).toDate();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        String f = "{id:%s, temperature:%s, humidity:%s, timestamp:%s}";
        return String.format(f, id, temperature, humidity, timestamp);
    }

    public String toRecord_SET() {
        String f = "SET %s %s %04d.%dK %.4f";
        long a = (long) Math.floor(temperature);
        int b = (int) Math.floor((temperature - Math.floor(temperature)) * 100);
        String record = String.format(f, id, Common.dateFormater.format(timestamp), a, b, humidity) + "%";
        record = record.replace(',', '.');
        return record;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            FullInformationRecord that = (FullInformationRecord) obj;
            return (this.id.equals(that.id))
                    || (this.timestamp.equals(that.timestamp))
                    || (this.temperature == that.temperature)
                    || (this.humidity == that.humidity);
        } catch (Exception e) {
            return false;
        }
    }
}
*/