package pl.skrzypekmichal.movementclassifier;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import pl.skrzypekmichal.movementclassifier.neural_network_models.features.SensorFeatures;
import pl.skrzypekmichal.movementclassifier.neural_network_models.features.SensorFeaturesProcessor;

import static org.junit.Assert.assertEquals;

public class SensorFeaturesProcessorTest {

    List<Float> rawData;
    SensorFeatures sensorFeatures;

    @Before
    public void setUp() throws Exception {
        rawData = new ArrayList<>();
        rawData.add(1.0f);
        rawData.add(1.0f);
        rawData.add(2.0f);
        rawData.add(2.0f);
        rawData.add(4.0f);
        rawData.add(6.0f);
        rawData.add(9.0f);

        sensorFeatures = SensorFeaturesProcessor.calculateSensorFeatures(rawData);
    }

    @Test
    public void testMad() {
        assertEquals(1.0f, sensorFeatures.getMad(), 0.0f);
    }

}
