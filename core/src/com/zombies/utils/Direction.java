package com.zombies.utils;

public enum Direction {
    TOP, TOP_LEFT, TOP_RIGHT, BOTTOM, BOTTOM_LEFT, BOTTOM_RIGHT, LEFT, RIGHT;

    public static Direction get(Vector vector){
        double arg = vector.getArg();
        double unit = 1/8. * Math.PI;
        if (arg > - unit && arg < unit){
            return TOP_RIGHT;
        }else if (arg > unit && arg < 3* unit){
            return TOP;
        }else if (arg > 3*unit && arg < 5* unit){
            return TOP_LEFT;
        }else if (arg > 5*unit && arg < 7* unit){
            return LEFT;
        }else if (arg > 7*unit || arg < - 7* unit){
            return BOTTOM_LEFT;
        }else if (arg > -7*unit && arg < - 5* unit){
            return BOTTOM;
        }else if (arg > -5*unit && arg < - 3* unit){
            return BOTTOM_RIGHT;
        }else if (arg > -3*unit && arg < - unit){
            return RIGHT;
        }
        return BOTTOM;
    }
}
