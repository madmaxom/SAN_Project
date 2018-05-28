// BluetoothDriver.h

#ifndef _BLUETOOTHDRIVER_h
#define _BLUETOOTHDRIVER_h

#if defined(ARDUINO) && ARDUINO >= 100
#include "arduino.h"
#else
#include "WProgram.h"
#endif
#include <SoftwareSerial.h>

#define START_BYTE '$'
#define STOP_BYTE '*'
#define START_COMMUNICATION "#"
#define SEPERATOR ';'
#define ACK '|'
#define NACK '?'

#define CONNECT_COMMAND "0:"
#define ACC_COMMAND "1:"
#define TEMP_COMMAND "2:"
#define VEL_COMMAND "3:"
#define LDR_COMMAND "4:"
#define DIST_COMMAND "5:"

class BluetoothDriver
{
public:
	BluetoothDriver();
	BluetoothDriver(int rx, int tx);
	~BluetoothDriver();
	bool IsConnected();
	void Disconnect();
	void Connect();
	void Send(const String& msg);
	bool Receive();
	SoftwareSerial serial;
private:
	bool connected = false;
	String inputString; 
};
#endif
