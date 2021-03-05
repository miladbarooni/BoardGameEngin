

#include <iostream>
#include "Engine.cpp"
#include <fstream>
#include <ctime>

//#include "windows.h" 



#include <stdio.h> 
#include <stdlib.h> 
#include <string.h>


using namespace std;


class XOManager : public Manager{

public:
	bool check_if_player_has_won(int player_number) {
		for (int i = 0; i < 3; i++) {
			if (this->board->getCell(i, 0)->getOwner() == this->getPlayer(player_number) &&
				this->board->getCell(i, 1)->getOwner() == this->getPlayer(player_number) &&
				this->board->getCell(i, 2)->getOwner() == this->getPlayer(player_number)) {
				this->player_has_won(player_number);
				return true;
			}

		}
		for (int i = 0; i < 3; i++) {
			if (this->board->getCell(0, i)->getOwner() == this->getPlayer(player_number) &&
				this->board->getCell(1, i)->getOwner() == this->getPlayer(player_number) &&
				this->board->getCell(2, i)->getOwner() == this->getPlayer(player_number)) {
				this->player_has_won(player_number);
				return true;
			}
				
		}
		if (this->board->getCell(0, 0)->getOwner() == this->getPlayer(player_number) &&
			this->board->getCell(1, 1)->getOwner() == this->getPlayer(player_number) &&
			this->board->getCell(2, 2)->getOwner() == this->getPlayer(player_number)) {
			this->player_has_won(player_number);
			return true;
		}

		if (this->board->getCell(0, 2)->getOwner() == this->getPlayer(player_number) &&
			this->board->getCell(1, 1)->getOwner() == this->getPlayer(player_number) &&
			this->board->getCell(2, 0)->getOwner() == this->getPlayer(player_number)) {
			this->player_has_won(player_number);
			return true;
		}
		return false;
	}

	bool check_if_draw() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (this->board->getCell(i, j)->getOwner() == NULL) return false;
			}
		}
		return true;
	}

	bool check_if_player_has_lost(int player_number) {return false; }


	void player_winning_message(int player_number) {
	}


	bool is_input_valid(Cell* cell) {
		return (cell->getOwner() == NULL);
	}

	bool are_inputs_valid(Cell* first_cell, Cell* second_cell) { return false; };

	void generateClocks() {
		clocks = new Clock[2];
		clocks[0].set_time(15);
		clocks[1].set_time(15);
	}



};


int main() { // main

	bool has_started = false;

	ifstream file;
	file.open("XO_communication_cpp.txt", std::ofstream::out | std::ofstream::trunc);
	file.close();

	clock_t start = clock();




	Manager* manager = new XOManager();
	manager->generateBoard(3, 3);
	
	manager->generateClocks();
	manager->setCurrentTurn(0);
	manager->setNumberOfPlayers(2);
	manager->setDoubleInput(false);
	manager->setHasClocks(false);
	manager->setHasDice(false);
	manager->setIsFinished(false);


	manager->generatePlayers(manager->getNumberOfPlayers());


	int r, c;
	Cell* cell;
	while (!manager->getIsFinished()) {

		char* message;

		do {
			// read from file 
			ifstream file;
			file.open("XO_communication_java.txt");
			while (file.peek() == ifstream::traits_type::eof()) {

				if (has_started) {

					if (float(clock() - start) / CLOCKS_PER_SEC > 1) {
						start = clock();
						manager->getClock(manager->getCurrentTurn())->run();

						if (manager->getClock(0)->is_finished()) {
							message = "1";
							// write to file
							ofstream file;
							file.open("XO_communication_cpp.txt");
							file.clear();
							file << message;
							file.close();
							// write to file
							return 0;
						}
						if (manager->getClock(1)->is_finished()) {
							message = "0";
							// write to file
							ofstream file;
							file.open("XO_communication_cpp.txt");
							file.clear();
							file << message;
							file.close();
							// write to file
							return 0;
						}

				}
				
				}
				file.close();
				file.open("XO_communication_java.txt");
			}
			has_started = true;
			file >> r;
			file >> c;
			file.close();
			file.open("XO_communication_java.txt", std::ofstream::out | std::ofstream::trunc);
			file.close();
			// read from file

			cell = manager->take_input(r, c);
		} while (!manager->is_input_valid(cell));

		cell->setOwner(manager->getPlayer(manager->getCurrentTurn()));

		if (manager->check_if_player_has_won(manager->getCurrentTurn())) {
			if (manager->getCurrentTurn() == 0)
				message = "0";
			if (manager->getCurrentTurn() == 1)
				message = "1";

		}

		else if (manager->check_if_draw()) message = "draw";
		else message = "done";
		// write to file
		ofstream file;
		file.open("XO_communication_cpp.txt");
		file.clear();
		file << message;
		file.close();
		// write to file


		if (manager->getIsFinished()) continue;

		manager->next_turn();


	}
	
	
	return 0;
}