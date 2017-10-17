package capgemini.webappdemo.utils;

public class CalculateMoney {
    // type of vehicle
    // the total length of the trip
    // the time of the trip

    //https://www.uber.com/vi-VN/drive/hanoi/resources/dong-san-pham/

    //https://www.grab.com/vn/bike/

    //https://www.grab.com/vn/car/

    public double getEstimateCost(String vehicle, double s, long t){
        double cost = 0;
        switch (vehicle){
            case "Uber X":
                    cost = 15 + 7.5*s + 0.3*t/60;
                    break;
            case "Uber Black" :
                    cost = 5 + 9.597*s + 0.8*t/60;
                    break;
            case "Uber SUV" :
                    cost = 5 + 9.597*s + 0.8*t/60;
                    break;
            case "Uber MOTO" :
                    cost = 10 + 3.7*s + 0.2*t/60;
                    break;
            case "Grab Bike":
                    if(s <= 2){
                        cost = 12;
                    } else{
                        cost = 12 + 3.8*(s-2);
                    }
                    break;
            case "Grab Bike Premium":
                    if(s <= 2){
                        cost = 20;
                    } else{
                        cost = 20 + 7*(s-2);
                    }
                    break;
            case "Grab Car 4 seats":
                    if(s <= 2){
                        cost = 20 + 0.3*t/60;
                    } else{
                        cost = 20 + 9*s + 0.3*t/60;
                    }
                    break;
            case "Grab Car 7 seats":
                    if(s <= 2){
                        cost = 24 + 0.3*t/60;
                    } else{
                        cost = 24 + 11*s + 0.3*t/60;
                    }
                    break;
            default: cost = 0; break;
        }
        return cost;
    }
}
