#include "MessageBuilder.h"


MessageBuilder::MessageBuilder()
{
}

void MessageBuilder::BuildResponse(const String msg, const String command, char* outStr)
{
	String message = String();
	message += START_BYTE;
	message += command;
	message += msg;
	message += STOP_BYTE;
	strcpy(outStr, message.c_str());
}

