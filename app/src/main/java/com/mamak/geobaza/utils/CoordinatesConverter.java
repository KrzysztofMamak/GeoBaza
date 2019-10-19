package com.mamak.geobaza.utils;

//TODO Refactor
public abstract class CoordinatesConverter {
    public static double[] tr2000WGS(double[] xy){
        double[] wsp={1,1};

        double x = xy[0];
        double y = xy[1];
        int strefa=2;

        double e=0.003356551485597;
        double v2=0.00000657;
        double v3=1.764656426454E-08;
        double v4=5.40048218776E-11;

        double a2=-0.0008377321681641;
        double a4=-5.905869626083E-08;
        double a6=-1.673488904988E-10;
        double a8=-2.167737805597E-13;
        double Ro=6367449.14577;
        double mo=0.999923;

        strefa= Integer.parseInt(String.valueOf(y).substring(0, 1));
        double v5 = (x/mo) / Ro;
        double Ygk = 0;
        double Lo = 0;

        switch (strefa) {
            case 5:  Ygk = (y - (double)5500000) / mo; Lo = 15; break;
            case 6:  Ygk = (y - (double)6500000) / mo; Lo = 18; break;
            case 7:  Ygk = (y - (double)7500000) / mo; Lo = 21; break;
            case 8:  Ygk = (y - (double)8500000) / mo; Lo = 24; break;
        }

        double V35 = (Ygk / Ro);

        double Xmer = v5 + (a2 * Math.sin(2 * v5) * Math.cosh(2 * V35) + a4 * Math.sin(4 * v5) * Math.cosh(4 * V35) + a6 * Math.sin(6 * v5) * Math.cosh(6 * V35) + a8 * Math.sin(8 * v5) * Math.cosh(8 * V35));
        double Ymer = V35 + (a2 * Math.cos(2 * v5) * Math.sinh(2 * V35) + a4 * Math.cos(4 * v5) * Math.sinh(4 * V35) + a6 * Math.cos(6 * v5) * Math.sinh(6 * V35) + (a8 * Math.cos(8 * v5) * Math.sinh(8 * V35)));
        double V27 = 2 * Math.atan(Math.exp(Ymer)) - Math.PI / 2;
        double fi=Math.asin(Math.cos(V27)*Math.sin(Xmer));
        double dlambda=Math.atan((Math.tan(V27))/Math.cos(Xmer));

        double v7=fi+e*Math.sin(2*fi)+v2*Math.sin(4*fi)+v3*Math.sin(6*fi)+v4*Math.sin(8*fi)+0.0000000008;

        wsp[0]=v7*  (180.0 / Math.PI);
        wsp[1]=(Lo + (dlambda * (180.0 / Math.PI)));

        return wsp;
    }

    public static double[] trWGS2000(double lat, double lon, int str){
        double[] wsp={1,1};

        double B = lat;
        double L = lon;

        int strefa=str;
        int Lo=18;

        switch (strefa) {
            case 1: Lo = 15; break;
            case 2: Lo = 18; break;
            case 3: Lo = 21; break;
            case 4: Lo = 24; break;
        }

        double mo = 0.999923;
        double RadB  = Math.toRadians(B);
        double RadL  = Math.toRadians(L);
        double RadLo = Math.toRadians(Lo);
        double Ro = 6367449.14577;
        double e = 0.0818191910428;
        double a2  = 0.0008377318247344;
        double a4  = 0.0000007608527788826;
        double a6  = 1.197638019173E-09;
        double a8  = 2.4433762425E-12;
        double fi  = 2*(Math.atan(Math.pow((1-e*Math.sin(RadB))/(1+e*Math.sin(RadB)),e/2)*Math.tan(RadB/2+Math.PI/4))-Math.PI/4);

        double Xmer = Math.atan(Math.sin(fi)/(Math.cos(fi)*Math.cos(RadL-RadLo)));
        double Ymer = 0.5*Math.log((1+Math.cos(fi)*Math.sin(RadL-RadLo))/(1-Math.cos(fi)*Math.sin(RadL-RadLo)));

        double Xgk =Ro*(Xmer+(a2*Math.sin(2*Xmer)*Math.cosh(2*Ymer))+(a4*Math.sin(4*Xmer)*Math.cosh(4*Ymer))+(a6*Math.sin(6*Xmer)*Math.cosh(6*Ymer))+(a8*Math.sin(8*Xmer)*Math.cosh(8*Ymer)));
        double Ygk =Ro*(Ymer+(a2*Math.cos(2*Xmer)*Math.sinh(2*Ymer))+(a4*Math.cos(4*Xmer)*Math.sinh(4*Ymer))+(a6*Math.cos(6*Xmer)*Math.sinh(6*Ymer))+(a8*Math.cos(8*Xmer)*Math.sinh(8*Ymer)));

        wsp[0]=mo*Xgk;

        switch (strefa) {
            case 1: wsp[1]=mo*Ygk+5500000; break;
            case 2: wsp[1]=mo*Ygk+6500000; break;
            case 3: wsp[1]=mo*Ygk+7500000; break;
            case 4: wsp[1]=mo*Ygk+8500000; break;
        }

        return wsp;
    }
}
