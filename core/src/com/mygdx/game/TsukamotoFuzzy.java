package com.mygdx.game;

import java.util.List;

public class TsukamotoFuzzy {
    public static float linear(float low, float high, float i) {
        return Math.min(1.0f, Math.max(0.0f, (i - low) / (high - low)));
    }

    public static float inferenceLinear(float low, float high, float i) {
        float test = i * (high - low) + low;
        System.out.println(i + " * (" + high + " - " + low + ") + " + low + " = " + test);
        return test;
    }

    public static float triangle(float low, float middle, float high, float i) {
        if (i <= middle)
            return linear(low, middle, i);
        else
            return linear(high, middle, i);
    }

    public static float defuzzTsukamoto(List<Float> input, List<Float> output) {
        float z = 0;
        for (int i = 0; i < input.size(); i++) {
            z += input.get(i) * output.get(i);
        }

        float preds = 0;
        for (float i: input) {
//            System.out.println(i);
            preds += i;
        }

        return z / preds;
    }
}
