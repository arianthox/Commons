
package com.arianthox.predictor.commons.hhm.dsp;


import java.util.*;

public class NFourierTransform {
    protected static int numPoints;
    private static List<Double> real;
    private static List<Double> imag;

    /**
     * Compute Furier<br>
     *
     * @param signal
     */
    public static Map.Entry<List<Double>,List<Double>> computeFFT(List<Double> signal) {
        numPoints = signal.size();
        real = new ArrayList<>(signal);
        imag = new ArrayList<>(signal);
        for (int i = 0; i < imag.size(); i++) {
            imag.set(i,0d);
        }
        process();
        return new AbstractMap.SimpleEntry<>(real,imag);
    }

    /**
     * Furier<br>
     */
    private static void process() {
        if(numPoints == 1) return;

        final double pi = Math.PI;
        final int numStages = (int) (Math.log(numPoints) / Math.log(2));

        int halfNumPoints = numPoints >> 1;
        int j = halfNumPoints;

        int k = 0;
        for (int i = 1; i < numPoints - 2; i++) {
            if (i < j) {
                double tempReal = real.get(j);
                double tempImag = imag.get(j);
                real.set(j,real.get(i));
                imag.set(j,imag.get(i));
                real.set(i,tempReal);
                imag.set(i,tempImag);
            }

            k = halfNumPoints;

            while (k <= j) {
                j -= k;
                k >>= 1;
            }

            j += k;
        }

        for(int stage = 1; stage <= numStages; stage++) {

            int LE = 1;
            for (int i = 0; i < stage; i++)
                LE <<= 1;

            int LE2 = LE >> 1;
            double UR = 1;
            double UI = 0;

            double SR = Math.cos(pi / LE2);
            double SI = -Math.sin(pi / LE2);

            for (int subDFT = 1; subDFT <= LE2; subDFT++) {
                for (int butterfly = subDFT - 1; butterfly <= numPoints - 1; butterfly += LE) {
                    int ip = butterfly + LE2;

                    double tempReal = real.get(ip) * UR - imag.get(ip) * UI;
                    double tempImag = real.get(ip) * UI + imag.get(ip) * UR;
                    real.set(ip, real.get(butterfly) - tempReal);
                    imag.set(ip, imag.get(butterfly) - tempImag);
                    real.set(butterfly,real.get(butterfly) + tempReal);
                    imag.set(butterfly,imag.get(butterfly) + tempImag);
                }

                double tempUR = UR;
                UR = tempUR * SR - UI * SI;
                UI = tempUR * SI + UI * SR;
            }
        }
    }
}
