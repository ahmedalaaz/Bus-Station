package clg.ahmedalansary.busstation;

public class Stop {
private String distanceToStop;
private String durationToStop;
private String name;

public Stop(String distanceToStop, String durationToStop, String name) {
	this.distanceToStop = distanceToStop;
	this.durationToStop = durationToStop;
	this.name = name;
}

@Override
public String toString() {
	return "Stop [distanceToStop=" + distanceToStop + ", durationToStop=" + durationToStop + ", name=" + name + "]";
}

public String getDistanceToStop() {
	return distanceToStop;
}
public void setDistanceToStop(String distanceToStop) {
	this.distanceToStop = distanceToStop;
}
public String getDurationToStop() {
	return durationToStop;
}
public void setDurationToStop(String durationToStop) {
	this.durationToStop = durationToStop;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}

}
