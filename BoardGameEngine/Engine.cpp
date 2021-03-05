#include <iostream>

using namespace std;




class Player {

private:
	char * input;

public:
	void setInput() {
		cin.getline(input, 200);
	}

	char* getInput() {
		return this->input;
	}



};


class Cell {

private:
	string color;
	string shape;
	Player* owner;
	int row, column;
	int next;
	bool has_next;

public:

	Cell(int r, int c) {
		this->row = r;
		this->column = c;
		this->color = "white";
		this->shape = "square";
		this->owner = NULL;
		this->next = 0;
		this->has_next = false;
	}

	Cell() { }

	void setRow(int r) {
		this->row = r;
	}

	void setColumn(int c) {
		this->column = c;
	}

	void setColor(string c) {
		this->color = c;
	}

	void setShape(string s) {
		this->shape = s;
	}

	void setOwner(Player* p) {
		this->owner = p;
	}

	string getColor() {
		return this->color;
	}

	string getShape() {
		return this->shape;
	}

	int getRow() {
		return this->row;
	}

	int getColumn() {
		return this->column;
	}

	Player* getOwner() {
		try{
			return this->owner;
		}
			
		catch (exception e) {
			return NULL;
		}
		
	}

	void setNext(int x) {
		this->next = x;
		this->has_next = true;
	}

	int getNext() {
		return this->next;
	}

	void setHasNext(bool hn) {
		this->has_next = hn;
	}

	bool getHasNext() {
		return this->has_next;
	}


};



class Board {

private:
	int rows, columns;
	Cell ** cells;

public:


	Board(int r, int c) {
		this->rows = r;
		this->columns = c;
		cells = new Cell*[r];
		for (int i = 0; i < r; i++) {
			cells[i] = new Cell[c];
		}
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				cells[i][j].setColor("white");
				cells[i][j].setOwner(NULL);
				cells[i][j].setShape("square");
				cells[i][j].setRow(i);
				cells[i][j].setColumn(j);
				cells[i][j].setHasNext(false);
				cells[i][j].setNext(0);

			}
		}

	}

	Board() {}

	~Board() {
		for (int i = 0; i < rows; i++)
		{
			delete(cells[rows]);
		}
	}

	void setRows(int r) {
		this->rows = r;
	}

	void setColumns(int c) {
		this->columns = c;
	}

	int getRows() {
		return this->rows;
	}

	int getColumns() {
		return this->columns;
	}

	Cell* getCell(int r, int c) {
		return &cells[r][c];
	}

};




class Clock {

private:

	int time;
	bool running = false;

public:

	void start() {
		this->running = true;
	}

	void pause() {
		this->running = false;
	}

	void run() { 
		if (time) time -= 1;
	};

	bool is_finished() {
		return !(time);
	}

	void set_time(int t) {
		this->time = t;
	}

	int get_time() {
		return this->time;
	}
};


class Piece {

private:
	Cell* cell;
	string character;
	Player* owner;
	Board* board;


public:
	Piece() {

	}
	void setBoard(Board* b) {
		this->board = b;
	}
	Piece(Board* board) {
		this->board = board;
	}
	Board* getBoard() {
		return this->board;
	}


	Piece(int r, int c) {
		this->cell = board->getCell(r, c);
	}

	void setCell(Cell* c) {
		this->cell = c;
	}

	void setCharacter(string ch) {
		this->character = ch;
	}

	void setOwner(Player* p) {
		this->owner = p;
	}

	Cell* getCell() {
		return this->cell;
	}

	string getCharacter() {
		return this->character;
	}

	Player* getOwner() {
		return this->owner;
	}


};

class Manager {

private:

	int number_of_players;
	int current_turn;
	bool double_input;
	bool has_clocks;
	bool has_dice;

	bool is_finished;

protected:
	Player* players;
	Clock* clocks;
	Board* board;


public:

	Clock* getClock(int i) {
		return &clocks[i];
	}

	Player* getPlayer(int n) {
		return &players[n];
	}

	void generatePlayers(int n) {
		players = new Player[n];
	}

	void generateBoard(int r, int c) {
		
		board = new Board(r, c);
		
	}

	void setHasDice(bool hd) {
		this->has_dice = hd;
	}

	bool getHasDice() {
		return this->has_dice;
	}

	void setHasClocks(bool hc) {
		this->has_clocks = true;
	}

	bool getHasClocks() {
		return this->has_clocks;
	}

	void genetate_clocks() {
		for (int i = 0; i < getNumberOfPlayers(); i++) {
			clocks[i] = Clock();
		}
	}


	void setNumberOfPlayers(int n) {
		this->number_of_players = n;
	}

	int getNumberOfPlayers() {
		return this->number_of_players;
	}

	void setCurrentTurn(int ct) {
		this->current_turn = ct;
	}

	int getCurrentTurn() {
		return this->current_turn;
	}

	void next_turn() {
		if (getCurrentTurn() +1 == getNumberOfPlayers()) setCurrentTurn(0);
		else setCurrentTurn(getCurrentTurn() + 1);
	}

	void setDoubleInput(bool di) {
		this->double_input = di;
	}

	bool getDoubleInput() {
		return this->double_input;
	}

	virtual bool check_if_player_has_won(int player_number) = 0;

	virtual bool check_if_player_has_lost(int player_number) = 0;

	bool check_if_player_is_run_out_of_time(int player_number) {
		return clocks[player_number].is_finished();
	}
	
	virtual bool check_if_draw() = 0;

	void player_has_won(int player_number) {
		setIsFinished(true);
		player_winning_message(player_number);
	}
	virtual void player_winning_message(int player_number) = 0;// 1. play again, 2.exit //

	void start_time(int clock_number) {
		clocks[clock_number].start();
	}

	void pause_time(int clock_number) {
		clocks[clock_number].pause();
	}


	Cell* take_input(int r, int c) {
		return board->getCell(r, c);
	}

	virtual bool is_input_valid(Cell* cell) = 0;

	virtual bool are_inputs_valid(Cell* first_cell, Cell* second_cell) = 0;

	bool getIsFinished() {
		return this->is_finished;
	}

	void setIsFinished(bool isf) {
		this->is_finished = isf;
	}

	virtual int throwDice() { return 0; }
	virtual void make_change(int dice) {}
	virtual void setOwners() {}
	virtual void generateNexts() {}
	virtual Piece* getPieces(int index) { return NULL; }
	virtual void generateClocks() {}


};

