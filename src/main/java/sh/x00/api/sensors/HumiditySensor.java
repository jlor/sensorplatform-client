package sh.x00.api.sensors;

import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.GpioUtil;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sh.x00.api.model.SensorOutput;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
//TODO[jlor]: Refactor me to better support unit-testing and starting outside hardware
@Alternative
@ApplicationScoped
public class HumiditySensor implements Sensor {
    private static final Logger log = LoggerFactory.getLogger( HumiditySensor.class );

    @ConfigProperty(name = "dht22.pin.out")
    Integer DHT22_PIN_OUT;
    @ConfigProperty(name = "dht22.pin.data")
    Integer DHT22_PIN_DATA;

    private String sensorType = "DHT22";
    private boolean initialized = false;

    @Override
    public String getSensorType() {
        return sensorType;
    }

    @Override
    public SensorOutput getSensorOutput(String sensorName) {
        //TODO: Naming scheme for individual DHT22s
        dht22data pollData = poll();
        SensorOutput sensorOutput = null;
        if (pollData.parityCheck()) {
            sensorOutput = new SensorOutput(sensorType, sensorType,
                    Arrays.asList(
                            Double.parseDouble(Float.toString(pollData.getHumidity())),
                            Double.parseDouble(Float.toString(pollData.getTemperature()))
                    )
            );
        }
        return sensorOutput;
    }

    @Override
    public List<SensorOutput> getSensorOutputList() {
        //TODO: Allow multiple DHT22s
        return Collections.singletonList(getSensorOutput(null));
    }

    @PostConstruct
    public void init() {
        if (Gpio.wiringPiSetup() == -1) {
            log.warn("Unable to to setup GPIO for DHT22 sensor!");
            return;
        }

        GpioUtil.export(DHT22_PIN_OUT, GpioUtil.DIRECTION_OUT);
        initialized = true;
    }

    private dht22data poll() {
        dht22data data = new dht22data();
        int lastState = Gpio.HIGH;

        // Set data pin to LOW, wait 18ms
        Gpio.pinMode(DHT22_PIN_DATA, Gpio.OUTPUT);
        Gpio.digitalWrite(DHT22_PIN_DATA, Gpio.LOW);
        Gpio.delay(18);

        // Set data pin to HIGH and read
        Gpio.digitalWrite(DHT22_PIN_DATA, Gpio.HIGH);
        Gpio.pinMode(DHT22_PIN_DATA, Gpio.INPUT);

        for (int i = 0; i < 85; i++) {
            int counter = 0;
            // Wait until data pin goes LOW or for 255ms
            while (Gpio.digitalRead(DHT22_PIN_DATA) == lastState) {
                counter++;
                Gpio.delayMicroseconds(1);
                if (counter == 255) {
                    return data;
                }
            }

            lastState = Gpio.digitalRead(DHT22_PIN_DATA);

            if (i >= 4 && i % 2 == 0) {
                data.shiftDataBit();
                if (counter > 16) data.xorDataBit();

                data.increasePollData();
            }
        }
        return data;
    }

    private class dht22data {
        int pollData = 0;
        int[] data = {0, 0, 0, 0, 0};

        void increasePollData() {
            pollData++;
        }

        void shiftDataBit() {
            data[pollData / 8] <<= 1;
        }

        void xorDataBit() {
            data[pollData / 8] |= 1;
        }

        float getTemperature() {
            float tempTemperature = (float) (((data[2] & 0x7F) << 8) + data[3]) / 10;
            if (tempTemperature > 125) {
                return data[2]; // for DHT22
            }
            if ((data[2] & 0x80) != 0) {
                return -tempTemperature;
            }
            return tempTemperature;
        }

        float getHumidity() {
            float tempHumidity = (float) ((data[0] << 8) + data[1]) / 10;
            if (tempHumidity > 100) {
                tempHumidity = data[0];
            }
            return tempHumidity;
        }

        boolean parityCheck() {
            return pollData > 40 &&
                    (data[4] == (data[0] + data[1] + data[2] + data[3] & 0xFF));
        }
    }

}
*/