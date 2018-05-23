// SpeedDriver.h

#ifndef _SPEEDDRIVER_h
#define _SPEEDDRIVER_h

#if defined(ARDUINO) && ARDUINO >= 100
	#include "arduino.h"
#else
	#include "WProgram.h"
#endif
#include <SoftwareSerial.h>

class SpeedDriver
{
public:
	SpeedDriver();
	SpeedDriver(int rx, int tx);
	~SpeedDriver();
	void GetRawData(double* values) const;

	double GetRawX() const;
	double GetRawY() const;
	double GetRawZ() const;

	int pinX;
	int pinY;
	int pinZ;

	SoftwareSerial serial;
private:
	bool connected = false;
	String inputString;
};

#endif