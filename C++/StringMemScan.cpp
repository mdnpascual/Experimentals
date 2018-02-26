// StringMemScan.cpp : Attaches to a process and searches for a string in memory (Like cheat engine)
//

#include "stdafx.h"
#include "windows.h"
#include "iostream"
#include "vector"
#include "string"
#include "sstream"
#include "algorithm"
#include "iterator"
#include "list"
using namespace std;

DWORD pid;

//Source https://stackoverflow.com/questions/10372872/read-process-memory-of-a-process-does-not-return-everything/10373209#10373209
template <class InIter1, class InIter2, class OutIter>
void find_all(unsigned char *base, InIter1 buf_start, InIter1 buf_end, InIter2 pat_start, InIter2 pat_end, OutIter res) {
	for (InIter1 pos = buf_start;
		buf_end != (pos = search(pos, buf_end, pat_start, pat_end));
		++pos)
	{
		*res++ = base + (pos - buf_start);
	}
}

template <class outIter>
void find_locs(HANDLE process, string const &pattern, outIter output) {

	unsigned char *p = NULL;
	MEMORY_BASIC_INFORMATION info;

	for (p = NULL;
		VirtualQueryEx(process, p, &info, sizeof(info)) == sizeof(info);
		p += info.RegionSize)
	{
		vector<char> buffer;
		vector<char>::iterator pos;

		if (info.State == MEM_COMMIT &&
			(info.Type == MEM_MAPPED || info.Type == MEM_PRIVATE))
		{
			SIZE_T bytes_read;
			buffer.resize(info.RegionSize);
			ReadProcessMemory(process, p, &buffer[0], info.RegionSize, &bytes_read);
			buffer.resize(bytes_read);
			find_all(p, buffer.begin(), buffer.end(), pattern.begin(), pattern.end(), output);
		}
	}
}

//Scans memory with a string pattern and returns a list of addresses where they are found
list<string> findAddressPatternInMem(HANDLE Handler, string pattern){
	stringstream buffer;
	list<string> addresses;
	find_locs(Handler, pattern, ostream_iterator<void *>(buffer, "\n"));
	string output = buffer.str();
	buffer.str(string());

	string delimiter = "\n";
	size_t pos = 0;
	while ((pos = output.find(delimiter)) != string::npos) {
		addresses.push_back(output.substr(0, pos));
		output.erase(0, pos + delimiter.length());
	}

	return addresses;
}

int _tmain(int argc, _TCHAR* argv[])
{
	HWND hWND = FindWindowA(0, ("Replace with Process Window name"));
	list<string> addresses;

	GetWindowThreadProcessId(hWND, &pid);
	HANDLE pHandle = OpenProcess(PROCESS_VM_OPERATION | PROCESS_VM_READ | PROCESS_VM_WRITE | PROCESS_QUERY_INFORMATION, FALSE, pid);

	if (pHandle == INVALID_HANDLE_VALUE){
		cout << "Can't attach to process" << endl;
		return 0;
	}
	else{
		addresses = findAddressPatternInMem(pHandle, "String_To_Search");
		
		//Iterate on list
		list<string>::iterator iter = addresses.begin();
		while (iter != addresses.end()){
			//Process something inside list
			cout << *iter << endl;
			iter++;
		}
		
	}
	system("pause");
	return 0;
}


