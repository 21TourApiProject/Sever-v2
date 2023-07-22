package com.server.tourApiProject.observation;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class ObservationFitTest {

    @org.junit.jupiter.api.Test
    public void getObservationFitTest() {

        double[] observationFit = getObservationFit(40D, 28D, 0.21D, "보통", 40D, 40D);
        System.out.println("observationFit[0] = " + observationFit[0]);
        System.out.println("observationFit[1] = " + observationFit[1]);
    }

    public double[] getObservationFit(Double clouds, Double feelLike, Double moonPhase,
                                      String fineDust, Double pop, Double lightPollution) {
        double[] values = {getCloudsValue(clouds),
                getFeelLikeValue(feelLike),
                getMoonPhaseValue(moonPhase),
                getFineDustValue(fineDust),
                getPopValue(pop),
                getLightPollutionValue(lightPollution)};

        double minValue = Arrays.stream(values)
                .min()
                .orElseThrow(() -> new IllegalStateException("Array is empty."));
        double otherSumValue = Math.round((Arrays.stream(values).sum() - minValue) / 6.0 * 100) / 100.0;
        double total = total(minValue, otherSumValue);
        int idx = minIdx(values);
        return new double[]{total, idx};
    }

    public double getCloudsValue(Double clouds) {
        return Math.round(100 * (-(1 / (-0.25 * (clouds / 100 - 2.7)) - 1.48148)) * 100) / 100.0;
    }

    public double getFeelLikeValue(Double feelLike) {
        double feelLikeValue;
        if (feelLike < 18) {
            feelLikeValue = -0.008 * Math.pow((feelLike - 18), 2);
        } else {
            feelLikeValue = -0.09 * Math.pow((feelLike - 18), 2);
        }
        return Math.round(feelLikeValue * 100) / 100.0;
    }

    public double getMoonPhaseValue(Double moonPhase) {
        double moonPhaseValue;
        if (moonPhase <= 0.5) {
            moonPhaseValue = -((8 * Math.pow(moonPhase, 3.46)) / 0.727 * 100);
        } else if (moonPhase <= 0.5609) {
            moonPhaseValue = -((75 * Math.pow(moonPhase - 0.5, 2) - 0.727) / 0.727 * 100);
        } else {
            moonPhaseValue = -((1 / (5.6 * (Math.pow(moonPhase + 0.3493, 10))) - 0.0089) / 0.727 * 100);
        }
        return Math.round(moonPhaseValue * 100) / 100.0;
    }

    public double getFineDustValue(String fineDust) {
        double fineDustValue;
        switch (fineDust) {
            case "보통":
                fineDustValue = -5D;
                break;
            case "나쁨":
                fineDustValue = -15D;
                break;
            case "매우나쁨":
                fineDustValue = -30D;
                break;
            default:
                fineDustValue = 0D;
                break;
        }
        return fineDustValue;
    }

    public double getPopValue(Double pop) {
        return Math.round(100 * (-(1 / (-(1.2) * (pop / 100 - 1.5)) - 0.55556)) * 100) / 100.0;
    }

    public double getLightPollutionValue(Double lightPollution) {
        double lightPollutionValue;
        if (lightPollution < 28.928) {
            lightPollutionValue = -(1 / (-(0.001) * (lightPollution - 48)) - 20.833);
        } else if (lightPollution < 77.53) {
            lightPollutionValue = -(1 / (-(0.0001) * (lightPollution + 52)) + 155);
        } else if (lightPollution < 88.674) {
            lightPollutionValue = -(1 / (-(0.001) * (lightPollution - 110)) + 47);
        } else {
            lightPollutionValue = -(1 / (-(0.01) * (lightPollution - 71)) + 100);
        }
        return Math.round(lightPollutionValue * 100) / 100.0;
    }

    public int minIdx(double[] values) {
        double min = values[0];
        int minIdx = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] < min) {
                min = values[i];
                minIdx = i;
            }
        }
        return minIdx;
    }

    public double total(Double minValue, Double otherSumValue) {
        double value = 100 + (minValue + otherSumValue * 0.5);
        return (value > 0) ? (Math.round(value * 100) / 100.0) : 0;
    }

}
