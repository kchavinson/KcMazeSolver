/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam
 * @version 03/10/2023
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {
        ArrayList<MazeCell> solutionPath = new ArrayList<>();
        Stack<MazeCell> mazeCells = new Stack<>();
        MazeCell currentCell = maze.getEndCell();

        // Solve going backwards starting from end cell
        while (currentCell != null)
        {
            mazeCells.push(currentCell);
            // If you are the start, stop
            if (currentCell == maze.getStartCell())
            {
                break;
            }
            currentCell = currentCell.getParent();
        }
        // Stack is Lifo so add all of it into an arraylist to get correct path
        while (!mazeCells.isEmpty())
        {
            solutionPath.add(mazeCells.pop());
        }
        return solutionPath;
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        // TODO: Use DFS to solve the maze
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        MazeCell currentCell = maze.getStartCell();
        Stack<MazeCell> mazeCells = new Stack<MazeCell>();

        while (currentCell != maze.getEndCell())
        {
            // Gets the current row and col
            int row = currentCell.getRow();
            int col = currentCell.getCol();

            // In the directional order, it sees if it can add the cells to the stack and adds last one
            checkStackCell(row -1, col, currentCell, mazeCells);
            checkStackCell(row, col +1, currentCell, mazeCells);
            checkStackCell(row + 1, col, currentCell, mazeCells);
            checkStackCell(row, col -1, currentCell, mazeCells);
            currentCell = mazeCells.pop();
        }
        // Returns solution
        return getSolution();
    }
    public void checkStackCell(int row, int col, MazeCell currentCell, Stack mazeCells){
        // Makes sure the cell hasn't been explored and is valid, then adds it to stack if it is and explores it aswell
        if (maze.isValidCell(row, col))
        {
            mazeCells.push(maze.getCell(row , col));
            maze.getCell(row, col).setExplored(true);
            maze.getCell(row, col).setParent(currentCell);
        }
    }

    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // TODO: Use BFS to solve the maze
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        MazeCell currrentCell = maze.getStartCell();
        Queue<MazeCell> mazeCells = new LinkedList<MazeCell>();

        while (currrentCell != maze.getEndCell())
        {
            // Gets row and column of the current cell
            int row = currrentCell.getRow();
            int col = currrentCell.getCol();

            // In the directional order, it sees if it can add the cells to the queue
            checkQueueCell(row -1, col, currrentCell, mazeCells);
            checkQueueCell(row, col +1, currrentCell, mazeCells);
            checkQueueCell(row +1, col, currrentCell, mazeCells);
            checkQueueCell(row, col -1, currrentCell, mazeCells);
            currrentCell = mazeCells.poll();
        }
        // Returns solution
        return getSolution();
    }

    public void checkQueueCell(int row, int col, MazeCell currentCells, Queue mazeCells){
        // Makes sure the cell hasn't been explored and is valid, then adds it to queue if it is and explores it aswell
        if (maze.isValidCell(row, col))
        {
            mazeCells.add(maze.getCell(row , col));
            maze.getCell(row, col).setExplored(true);
            maze.getCell(row, col).setParent(currentCells);
        }
    }

    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}
