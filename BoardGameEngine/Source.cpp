#include <iostream>

using namespace std;


class Engine {

public:
	void is_win();

	int getNumberofPlayers()
	{
		return 2;
	}
};

class XO : public Engine {

public:

	static void is_win() {
		cout << "Yes";
	}
	
};


int main()
{
	
	XO::is_win();
}