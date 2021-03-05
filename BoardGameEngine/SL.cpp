#include "Engine.cpp"
#include <iostream>
#include <fstream>
#include <ctime>

using namespace std;



class SLManager :public Manager
{
	Piece* pieces;
public:


	bool check_if_player_has_won(int player_number) {
		if (pieces[player_number].getCell()->getColumn() == 99) {
			player_has_won(player_number);
			return true;

		}
		return false;

	}

	bool check_if_player_has_lost(int player_number) {
		return false;
	}
	bool check_if_draw() {
		return false;
	};
	void player_winning_message(int player_number) {
	}
	bool is_input_valid(Cell* cell) { return true; }
	bool are_inputs_valid(Cell* first_cell, Cell* second_cell)
	{
		return false;
	}

	void make_change(int dice) {
		int index = this->getCurrentTurn();

		int cell_column = pieces[index].getCell()->getColumn();
		if (cell_column + dice > 99) return;

		cell_column += dice;


		if (this->board->getCell(0, cell_column)->getHasNext()) cell_column += this->board->getCell(0, cell_column)->getNext();


		pieces[index].setCell(this->board->getCell(0, cell_column));



	}
	int throwDice() {
		int num = rand() % 6 + 1;
		return num;
	}
	void setOwners() {
		pieces = new Piece[getNumberOfPlayers()];
		Piece temp;
		for (int i = 0; i<getNumberOfPlayers(); i++) {
			pieces[i].setOwner(this->getPlayer(i));
			pieces[i].setCell(this->board->getCell(0, 0));
		}


	}
	void generateNexts() {

		this->board->getCell(0, 3)->setNext(10);
		this->board->getCell(0, 8)->setNext(22);
		this->board->getCell(0, 19)->setNext(18);
		this->board->getCell(0, 24)->setNext(58);
		this->board->getCell(0, 39)->setNext(19);
		this->board->getCell(0, 62)->setNext(18);
		this->board->getCell(0, 70)->setNext(20);
		this->board->getCell(0, 50)->setNext(16);
		
		this->board->getCell(0, 16)->setNext(-10);
		this->board->getCell(0, 53)->setNext(-20);
		this->board->getCell(0, 61)->setNext(-43);
		this->board->getCell(0, 63)->setNext(-4);
		this->board->getCell(0, 86)->setNext(-63);
		this->board->getCell(0, 92)->setNext(-20);
		this->board->getCell(0, 94)->setNext(-20);
		this->board->getCell(0, 98)->setNext(-21);





		// write to file
		ofstream file;
		file.open("SaL_communication_cpp.txt");
		file.clear();
		file << "10 3\n22 8\n18 19\n58 24\n19 39\n18 62\n20 70\n16 50\n-10 16\n-20 53\n-43 61\n-4 63\n-63 86\n-20 92\n-20 94\n-21 98";
		file.close();
		// write to file

	}

	Piece* getPieces(int index) {
		return &(this->pieces[index]);
	}
};




int main2() { // main

	Manager* manager = new SLManager();
	manager->generateBoard(1, 100);
	manager->setCurrentTurn(0);
	manager->generateNexts();
	manager->setNumberOfPlayers(4);
	manager->setDoubleInput(false);
	manager->setHasClocks(false);
	manager->setHasDice(true);
	manager->setIsFinished(false);
	manager->setOwners();
	manager->generatePlayers(manager->getNumberOfPlayers());

	int input;
	srand(time(0));

	while (!manager->getIsFinished()) {
		// read from file 
		ifstream file;
		file.open("SaL_communication_java.txt");
		while (file.peek() == ifstream::traits_type::eof()) {
			file.close();
			file.open("SaL_communication_java.txt");
		}
		file >> input;
		file.close();
		file.open("SaL_communication_java.txt", std::ofstream::out | std::ofstream::trunc);
		file.close();
		// read from file


		int result = manager->throwDice();


		manager->make_change(result);




		int c_turn = manager->getCurrentTurn();
		if (manager->check_if_player_has_won(c_turn)) {
			// write to file
			ofstream file;
			file.open("SaL_communication_cpp.txt");
			file.clear();
			
			file << result << " " << manager->getPieces(manager->getCurrentTurn())->getCell()->getColumn() << " " << 1;
			file.close();
			// write to file

			continue;
		}

		else {
			// write to file
			ofstream file;
			file.open("SaL_communication_cpp.txt");
			file.clear();
			cout << manager->getPieces(manager->getCurrentTurn())->getCell()->getColumn();
			cout << endl;
			file << result << " " << manager->getPieces(manager->getCurrentTurn())->getCell()->getColumn() << " " << 0;
			file.close();
			// write to file


		}

		manager->next_turn();




	}
	return 0;
}