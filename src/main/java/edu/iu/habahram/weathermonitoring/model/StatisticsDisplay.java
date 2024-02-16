package edu.iu.habahram.weathermonitoring.model;

import org.springframework.stereotype.Component;

@Component
public class StatisticsDisplay implements Observer, DisplayElement {
    private float temperatureTotal = 0;

    private float avg = 0;
    private int count = 0;
    private float minTemp = 1000000000;
    private float maxTemp = 0;
    private Subject weatherData;

    public StatisticsDisplay(Subject weatherData) {
        this.weatherData = weatherData;
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        temperatureTotal += temperature;
        calculateAvg();
        if(temperature > maxTemp){
            this.maxTemp = temperature;
        }
        if(temperature < minTemp){
            this.minTemp = temperature;
        }
    }
    private void calculateAvg(){
        count++;
        avg = temperatureTotal / count;
    }
    public String name() {
        return "Statistics Display";
    }

    public String id() {
        return "statistics-display";
    }

    @Override
    public String display() {
        String html = "";
        html += String.format("<div style=\"background-image: " +
                "url(/images/sky.webp); " +
                "height: 400px; " +
                "width: 647.2px;" +
                "display:flex;flex-wrap:wrap;justify-content:center;align-content:center;" +
                "\">");
        html += "<section>";
        html += String.format("<label>Avg temperature: %s</label><br />", avg);
        html += String.format("<label>Min temperature: %s</label><br />", minTemp);
        html += String.format("<label>Max temperature: %s</label><br />", maxTemp);
        html += "</section>";
        html += "</div>";
        return html;
    }
    public void subscribe() {
        weatherData.registerObserver(this);
    }

    public void unsubscribe() {
        weatherData.removeObserver(this);
    }

}
