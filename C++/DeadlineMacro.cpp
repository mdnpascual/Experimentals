// DeadlineMacro.cpp : Accurate macro (up to 1ms accuracy) with deadline usage in windows.
// Windows doesn't have a nanosleep just like in linux so this is an implementation where
// A program can be slept in ms accuracy.
// Theoritical usage for TAS like macros for windows games
//

#include <iostream>
#include <boost/bind.hpp>
#include <boost/thread.hpp>
#include <boost/asio.hpp>
#include <boost/date_time/posix_time/posix_time.hpp>
#include <windows.h>
#include <conio.h>

#define WINVER 0x0500

using namespace boost::asio;
using namespace std;

// i = boolean, true = press, false = release press
// j == array length
// k == delay(ms), 1st value should always be offset	
// z == keypressed

bool i[] = { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 };
int j = 0;
int k[] = { 1, 450, 7250, 866, 4233, 1100, 1766, 1100, 3350, 566, 1050, 833, 3033, 1033, 4050, 1200, 1050, 700, 750, 587, 1666, 350, 1350, 300, 1900, 587, 1100, 1666, 3887, 366, 1233, 350, 2366, 600, 3350 };
int z[] = { 32, 73, 73, 73, 73, 73, 73, 73, 73, 73, 73, 73, 73, 73, 73, 73, 73, 73, 73, 73, 73, 73, 73, 73, 73, 73, 73, 73, 73, 73, 73, 73, 73, 73, 73 };
//123.8333
class Deadline
{
public:

	INPUT inp;

	Deadline(deadline_timer &timer) : t(timer) {
		inp.type = INPUT_KEYBOARD;
		inp.ki.wScan = 0; // hardware scan code for key
		inp.ki.time = 0;
		inp.ki.dwExtraInfo = 0;
		wait();
	}

	void timeout(const boost::system::error_code &e) {
		//if (e)
		//	return;
		if (j > 35) //24
			return;

		if (i[j]){
			// Press the a key
			inp.ki.wVk = z[j]; // virtual-key code for the "a" key
			inp.ki.dwFlags = 0; // 0 for key press
			SendInput(1, &inp, sizeof(INPUT));
		}
		else{
			// Release the a key
			inp.ki.wVk = z[j]; // virtual-key code for the "a" key
			inp.ki.dwFlags = KEYEVENTF_KEYUP; // KEYEVENTF_KEYUP for key release
			SendInput(1, &inp, sizeof(INPUT));
		}

		j++;
		wait();
	}

	void cancel() {
		t.cancel();
	}


private:
	void wait() {
		if (j == 0){
			t.expires_from_now(boost::posix_time::milliseconds(1)); //repeat rate here
		}
		else{
			t.expires_from_now(boost::posix_time::milliseconds(k[j])); //repeat rate here
		}
		t.async_wait(boost::bind(&Deadline::timeout, this, boost::asio::placeholders::error));
	}

	deadline_timer &t;
};


class CancelDeadline {
public:
	CancelDeadline(Deadline &d) :dl(d) { }
	void operator()() {
		string cancel;
		cin >> cancel;
		dl.cancel();
		return;
	}
private:
	Deadline &dl;
};



int main()
{
	bool x = false;
	while (!x){
		if (GetAsyncKeyState(82) < 0){	// Starting key to start
			x = true;
		}
	}

	io_service io;
	deadline_timer t(io);
	Deadline d(t);
	CancelDeadline cd(d);
	boost::thread thr1(cd);
	io.run();
	return 0;
}