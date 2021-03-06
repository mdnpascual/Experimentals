// PadPicExtract_multithreaded.cpp : Extracts textures from PuzzleandDragons files using
// Cody Watt's Puzzle & Dragons Texture Tool: https://github.com/codywatts/Puzzle-and-Dragons-Texture-Tool

#include "stdafx.h"
#include "iostream"
#include "string"
#include "Windows.h"

using namespace std;

string imgID(string orig)
{
	if (orig.length() > 2)
		return orig;
	else if (orig.length() == 2)
		return "0" + orig;
	else if (orig.length() == 1)
		return "00" + orig;
	else
		return "ERROR";
}

int main()
{
	string bcPath = "";
	cout << "Path for .bc files: ";
	getline(cin, bcPath);

	if (bcPath[bcPath.length() - 1] != '\\'){
		bcPath += "\\";
	}

	int i = 0;
	//Get cards_*.bc
	while (i < 43) {
		string command = "/C python PADTextureTool.py \"" + bcPath + "cards_" + imgID(to_string(i)) + ".bc\"";
		wchar_t* command_WideStr = new wchar_t[command.length() + 1];
		copy(command.begin(), command.end(), command_WideStr);
		command_WideStr[command.length()] = 0;

		ShellExecute(0, L"open", L"cmd.exe", command_WideStr, 0, SW_HIDE);
		i++;
	}

	i = 0;
	//Get mons_*.bc
	while (i < 4185) {
		string command = "/C python PADTextureTool.py \"" + bcPath + "mons_" + imgID(to_string(i)) + ".bc\"";
		wchar_t* command_WideStr = new wchar_t[command.length() + 1];
		copy(command.begin(), command.end(), command_WideStr);
		command_WideStr[command.length()] = 0;

		ShellExecute(0, L"open", L"cmd.exe", command_WideStr, 0, SW_HIDE);
		i++;
	}

    return 0;
}

