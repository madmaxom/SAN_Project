// DisplayDriver.h

#ifndef _DISPLAYDRIVER_h
#define _DISPLAYDRIVER_h

#if defined(ARDUINO) && ARDUINO >= 100
#include "arduino.h"
#include <SoftwareSerial.h>

#else
#include "WProgram.h"
#endif
class DisplayDriver
{
public:
	DisplayDriver(); 
	~DisplayDriver(); 
	void ClearDisplay();
	void ResetCursor();
	void WriteFirstLine(const String& msg);
	void WriteSecondLine(const String& msg); 
	void WritePaging(char page); 
private:
	SoftwareSerial serial; 
	void ClearFistLine(); 
	void ClearSecondLine(); 
};
#endif
