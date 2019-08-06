// MessageBuilder.h

#ifndef _MESSAGEBUILDER_h
#define _MESSAGEBUILDER_h

#if defined(ARDUINO) && ARDUINO >= 100
	#include "arduino.h"
#else
	#include "WProgram.h"
#endif


#define START_BYTE '$'
#define STOP_BYTE '*'
#define START_COMMUNICATION "#####"
#define SEPERATOR ';'
#define ACK '|'
#define NACK '?'

#define CONNECT_COMMAND "0:"
#define ACC_COMMAND "1:"
#define TEMP_COMMAND "2:"
#define VEL_COMMAND "3:"
#define LDR_COMMAND "4:"
#define DIST_COMMAND "5:"

class MessageBuilder
{
public:
	MessageBuilder();
	void BuildResponse(String msg, String command, char* outStr);
private:
};
#endif

