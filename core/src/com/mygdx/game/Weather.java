package com.mygdx.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Weather {
    private static final Random random = new Random();
    private static boolean firstRun = true;
    private static float playTime;

    private static float[] pInferenceInput = new float[7];
    private static float[] pInferenceOutput = new float[7];
    private static float[] cInferenceInput = new float[3];
    private static float[] cInferenceOutput = new float[3];
    private static float[] rInferenceInput = new float[10];
    private static float[] rInferenceOutput = new float[10];

    //Calculated data members
    private static float windStrength;
    private static float windDirection;

    private static float rain;

    private static float rainNONE;
    private static final float rainNONE_A = 500;
    private static final float rainNONE_B = 0;

    private static float rainLOW;
    private static final float rainLOW_A = 0;
    private static final float rainLOW_B = 500;
    private static final float rainLOW_C = 1000;

    private static float rainHIGH;
    private static final float rainHIGH_A = 500;
    private static final float rainHIGH_B = 1000;
    
//    private static Rain rain = Rain.UNKNOWN;

    private static float precipitation;

    private static float pNONE;
    private static final float pNONE_A = 500;
    private static final float pNONE_B = 0;

    private static float pLOW;
    private static final float pLOW_A = 0;
    private static final float pLOW_B = 500;
    private static final float pLOW_C = 1000;

    private static float pHIGH;
    private static final float pHIGH_A = 500;
    private static final float pHIGH_B = 1000;

//    private static Precipitation precipitation = Precipitation.UNKNOWN;

    private static float change;

    private static float changeLOW;
    private static final float changeLOW_A = 500;
    private static final float changeLOW_B = 0;

    private static float changeNONE;
    private static final float changeNONE_A = 0;
    private static final float changeNONE_B = 500;
    private static final float changeNONE_C = 1000;

    private static float changeHIGH;
    private static final float changeHIGH_A = 500;
    private static final float changeHIGH_B = 1000;

//    private static Change change = Change.UNKNOWN;

    //Input data members

    private static float season;
    
    private static float seasonDRY;
    private static final float seasonLOW_A = 1000;
    private static final float seasonLOW_B = 0;

    private static float seasonWET;
    private static final float seasonHIGH_A = 0;
    private static final float seasonHIGH_B = 1000;


//    private static Season season;

    private static float prevPrecipitation;

    private static float ppNONE;
    private static final float ppNONE_A = 500;
    private static final float ppNONE_B = 0;

    private static float ppLOW;
    private static final float ppLOW_A = 0;
    private static final float ppLOW_B = 500;
    private static final float ppLOW_C = 1000;

    private static float ppHIGH;
    private static final float ppHIGH_A = 500;
    private static final float ppHIGH_B = 1000;

//    private static Precipitation prevPrecipitation;

    private static float cloud;

    private static float cNONE;
    private static final float cNONE_A = 500;
    private static final float cNONE_B = 0;

    private static float cLOW;
    private static final float cLOW_A = 0;
    private static final float cLOW_B = 500;
    private static final float cLOW_C = 1000;

    private static float cHIGH;
    private static final float cHIGH_A = 500;
    private static final float cHIGH_B = 1000;

    private static float prevCloud;

    public static int difficulty = 0;
    private static float windStrengthLimit;
    private static float windStrengthRamp;

    public static void resetWeather() {
        rain = 0;
        if (firstRun) {
            prevCloud = random.nextFloat();
        }
        else {
            prevPrecipitation = precipitation;
            prevCloud = cloud;
        }
        precipitation = 0;
    }

    public static void setWeather(float season) {
        if (firstRun) {
            Weather.season = season;

            float windStrengthMul;

            switch (difficulty) {
                case 1:
                    prevPrecipitation = random.nextFloat() * 500;
                    windStrengthMul = 0.1f;
                    windStrengthLimit = 0.08f;
                    windStrengthRamp = 960;
                    break;
                case 3:
                    prevPrecipitation = random.nextFloat() * 1000;
                    windStrengthMul = 0.4f;
                    windStrengthLimit = 0.15f;
                    windStrengthRamp = 360;
                    break;
                case 4:
                    prevPrecipitation = random.nextFloat() * 500 + 500;
                    windStrengthMul = 1;
                    windStrengthLimit = 0.3f;
                    windStrengthRamp = 240;
                    break;
                case 5:
                    prevPrecipitation = random.nextFloat() * 200 + 800;
                    windStrengthMul = 1.1f;
                    windStrengthLimit = 0.35f;
                    windStrengthRamp = 180;
                    break;
                default:
                    prevPrecipitation = random.nextFloat() * 500;
                    windStrengthMul = 0.125f;
                    windStrengthLimit = 0.1f;
                    windStrengthRamp = 480;
            }

            cloud = random.nextFloat() * 1000;

            windStrength = random.nextFloat() * windStrengthMul;
            windDirection = random.nextFloat() * 360;
            firstRun = false;
        }
        else {
            cloud += -300f + random.nextFloat() * 600f;
            cloud = Math.max(0, cloud);
            cloud = Math.min(cloud, 1000.0f);

            windStrength += -windStrengthLimit + random.nextFloat() * windStrengthLimit * 2;
            windStrength = Math.max(0 + playTime / windStrengthRamp, windStrength);
            windStrength = Math.min(windStrength, 1.0f + playTime / windStrengthRamp);

            windDirection += -45 + random.nextFloat() * 90;
            if (windDirection < 0) {
                windDirection = 360 + windDirection;
            }
            else if (windDirection > 360) {
                windDirection -= 360;
            }
        }
    }

    public static void calculateWeather() {
//        rainNONE = TsukamotoFuzzy.linear(rainNONE_A, rainNONE_B, rain);
//        rainLOW = TsukamotoFuzzy.triangle(rainLOW_A, rainLOW_B, rainLOW_C, rain);
//        rainHIGH = TsukamotoFuzzy.linear(rainHIGH_A, rainHIGH_B, rain);

//        if (prevCloud > cloud && Math.abs(prevCloud - cloud) >= 0.1f && isRuleActive[0]) {
//            change = Change.LOWER;
//            isRuleActive[7] = false;
//            System.out.println("R1");
//            return false;
//        }
//        cInferenceInput[6] = Math.min(ppHIGH, changeLOW);
//        cInferenceOutput[6] = TsukamotoFuzzy.inferenceLinear(pLOW_A, pLOW_B, pInferenceInput[6]);
//
//        if (Math.abs(prevCloud - cloud) < 0.1f && isRuleActive[1]) {
//            change = Change.NONE;
//            isRuleActive[8] = false;
//            System.out.println("R2");
//            return false;
//        }
//
//        if (prevCloud < cloud && Math.abs(prevCloud - cloud) >= 0.1f && isRuleActive[2]) {
//            change = Change.HIGHER;
//            isRuleActive[9] = false;
//            System.out.println("R3");
//            return false;
//        }

        change = random.nextFloat() * 333;
        if (Math.abs(prevCloud - cloud) >= 0.1f) {
            if (prevCloud < cloud)
                change += 667;
        }
        else
            change += 334;
        
        ppNONE = TsukamotoFuzzy.linear(ppNONE_A, ppNONE_B, prevPrecipitation);
        ppLOW = TsukamotoFuzzy.triangle(ppLOW_A, ppLOW_B, ppLOW_C, prevPrecipitation);
        ppHIGH = TsukamotoFuzzy.linear(ppHIGH_A, ppHIGH_B, prevPrecipitation);

        changeLOW = TsukamotoFuzzy.linear(changeLOW_A, changeLOW_B, change);
        changeNONE = TsukamotoFuzzy.triangle(changeNONE_A, changeNONE_B, changeNONE_C, change);
        changeHIGH = TsukamotoFuzzy.linear(changeHIGH_A, changeHIGH_B, change);
        
        cNONE = TsukamotoFuzzy.linear(cNONE_A, cNONE_B, cloud);
        cLOW = TsukamotoFuzzy.triangle(cLOW_A, cLOW_B, cLOW_C, cloud);
        cHIGH = TsukamotoFuzzy.linear(cHIGH_A, cHIGH_B, cloud);

        seasonDRY = TsukamotoFuzzy.linear(seasonLOW_A, seasonLOW_B, season);
        seasonWET = TsukamotoFuzzy.linear(seasonHIGH_A, seasonHIGH_B, season);

        System.out.println("ppNONE: " + ppNONE);
        System.out.println("ppLOW: " + ppLOW);
        System.out.println("ppHIGH: " + ppHIGH);
        
        System.out.print("changeLOW: " + changeLOW);
        System.out.print("changeNONE: " + changeNONE);
        System.out.println("changeHIGH: " + changeHIGH);
        
        System.out.print("cNONE: " + cNONE);
        System.out.print("cLOW: " + cLOW);
        System.out.println("cHIGH: " + cHIGH);
        
        System.out.print("seasonDRY: " + seasonDRY);
        System.out.println("seasonWET: " + seasonWET);

        pInferenceInput[0] = Math.min(ppNONE, Math.max(changeNONE, changeLOW));
        pInferenceOutput[0] = TsukamotoFuzzy.inferenceLinear(pNONE_A, pNONE_B, pInferenceInput[0]);

        pInferenceInput[1] = Math.min(ppNONE, changeHIGH);
        pInferenceOutput[1] = TsukamotoFuzzy.inferenceLinear(pLOW_A, pLOW_B, pInferenceInput[1]);

        pInferenceInput[2] = Math.min(ppLOW, changeNONE);
        pInferenceOutput[2] = TsukamotoFuzzy.inferenceLinear(pLOW_A, pLOW_B, pInferenceInput[2]);

        pInferenceInput[3] = Math.min(ppLOW, changeLOW);
        pInferenceOutput[3] = TsukamotoFuzzy.inferenceLinear(pNONE_A, pNONE_B, pInferenceInput[3]);

        pInferenceInput[4] = Math.min(ppLOW, changeHIGH);
        pInferenceOutput[4] = TsukamotoFuzzy.inferenceLinear(pHIGH_A, pHIGH_B, pInferenceInput[4]);

        pInferenceInput[5] = Math.min(ppHIGH, Math.max(changeNONE, changeHIGH));
        pInferenceOutput[5] = TsukamotoFuzzy.inferenceLinear(pHIGH_A, pHIGH_B, pInferenceInput[5]);

        pInferenceInput[6] = Math.min(ppHIGH, changeLOW);
        pInferenceOutput[6] = TsukamotoFuzzy.inferenceLinear(pLOW_A, pLOW_B, pInferenceInput[6]);

        {
            ArrayList<Float> pInfInput = new ArrayList<>();
            for (float i: pInferenceInput) {
//                System.out.println(i);
                pInfInput.add(i);
            }
            System.out.println();
            ArrayList<Float> pInfOutput = new ArrayList<>();
            for (float i: pInferenceOutput) {
//                System.out.println(i);
                pInfOutput.add(i);
            }
            precipitation = TsukamotoFuzzy.defuzzTsukamoto(pInfInput, pInfOutput);
        }

        pNONE = TsukamotoFuzzy.linear(pNONE_A, pNONE_B, precipitation);
        pLOW = TsukamotoFuzzy.triangle(pLOW_A, pLOW_B, pLOW_C, precipitation);
        pHIGH = TsukamotoFuzzy.linear(pHIGH_A, pHIGH_B, precipitation);

        System.out.println("pNONE: " + pNONE);
        System.out.println("pLOW: " + pLOW);
        System.out.println("pHIGH: " + pHIGH);

//        if (prevCloud > cloud && Math.abs(prevCloud - cloud) >= 0.1f && isRuleActive[0]) {
//            change = Change.LOWER;
//            isRuleActive[7] = false;
//            System.out.println("R1");
//            return false;
//        }
//        cInferenceInput[6] = Math.min(ppHIGH, changeLOW);
//        cInferenceOutput[6] = TsukamotoFuzzy.inferenceLinear(pLOW_A, pLOW_B, pInferenceInput[6]);
//
//        if (Math.abs(prevCloud - cloud) < 0.1f && isRuleActive[1]) {
//            change = Change.NONE;
//            isRuleActive[8] = false;
//            System.out.println("R2");
//            return false;
//        }
//
//        if (prevCloud < cloud && Math.abs(prevCloud - cloud) >= 0.1f && isRuleActive[2]) {
//            change = Change.HIGHER;
//            isRuleActive[9] = false;
//            System.out.println("R3");
//            return false;
//        }

        rInferenceInput[0] = Math.min(pNONE, seasonDRY);
        rInferenceOutput[0] = TsukamotoFuzzy.inferenceLinear(rainNONE_A, rainNONE_B, rInferenceInput[0]);

        rInferenceInput[1] = Math.min(Math.min(pNONE, seasonWET), cHIGH);
        rInferenceOutput[1] = TsukamotoFuzzy.inferenceLinear(rainLOW_A, rainLOW_B, rInferenceInput[1]);

        rInferenceInput[2] = Math.min(Math.min(pNONE, seasonWET), cNONE);
        rInferenceOutput[2] = TsukamotoFuzzy.inferenceLinear(rainNONE_A, rainNONE_B, rInferenceInput[2]);

        rInferenceInput[3] = Math.min(Math.min(pLOW, seasonDRY), cHIGH);
        rInferenceOutput[3] = TsukamotoFuzzy.inferenceLinear(rainLOW_A, rainLOW_B, rInferenceInput[3]);

        rInferenceInput[4] = Math.min(Math.min(pLOW, seasonDRY), cNONE);
        rInferenceOutput[4] = TsukamotoFuzzy.inferenceLinear(rainNONE_A, rainNONE_B, rInferenceInput[4]);

        rInferenceInput[5] = Math.min(Math.min(pLOW, seasonWET), cHIGH);
        rInferenceOutput[5] = TsukamotoFuzzy.inferenceLinear(rainHIGH_A, rainHIGH_B, rInferenceInput[5]);

        rInferenceInput[6] = Math.min(Math.min(pLOW, seasonWET), cLOW);
        rInferenceOutput[6] = TsukamotoFuzzy.inferenceLinear(rainLOW_A, rainLOW_B, rInferenceInput[6]);

        rInferenceInput[7] = Math.min(Math.min(pLOW, seasonWET), cNONE);
        rInferenceOutput[7] = TsukamotoFuzzy.inferenceLinear(rainNONE_A, rainNONE_B, rInferenceInput[7]);

        rInferenceInput[8] = Math.min(pHIGH, cHIGH);
        rInferenceOutput[8] = TsukamotoFuzzy.inferenceLinear(rainHIGH_A, rainHIGH_B, rInferenceInput[8]);

        rInferenceInput[9] = Math.min(pHIGH, cNONE);
        rInferenceOutput[9] = TsukamotoFuzzy.inferenceLinear(rainLOW_A, rainLOW_B, rInferenceInput[9]);

        {
            ArrayList<Float> rInfInput = new ArrayList<>();
            for (float i: rInferenceInput) {
                rInfInput.add(i);
            }
            ArrayList<Float> rInfOutput = new ArrayList<>();
            for (float i: rInferenceOutput) {
                rInfOutput.add(i);
            }
            rain = TsukamotoFuzzy.defuzzTsukamoto(rInfInput, rInfOutput);
        }
    }

    public static float getWindStrength() {
        return windStrength;
    }

    public static float getWindDirection() {
        return windDirection;
    }

    public static float getRain() {
        return rain;
    }

    public static float getSeason() {
        return season;
    }

    public static float getPrecipitation() {
        return precipitation;
    }

    public static float getCloud() {
        return cloud;
    }

    public static float getPrevCloud() {
        return prevCloud;
    }

    public static float getPrevPrecipitation() {
        return prevPrecipitation;
    }

    public static float getChange() {
        return change;
    }

    public static void setPlayTime(float playTime) {
        Weather.playTime = playTime;
    }

    public static float getFuzzifiedRain() {
        return TsukamotoFuzzy.linear(rainLOW_A, rainLOW_C, rain);
    }

    public static <T extends Enum<?>> T randomEnum(Class<T> enumClass) {
        int x = random.nextInt(enumClass.getEnumConstants().length);
        return enumClass.getEnumConstants()[x];
    }
}
