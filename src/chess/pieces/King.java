package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
	
	private ChessMatch chessMatch;

	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}
	
	@Override
	public String toString() {
		return "K";
	}

	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}
	
	private boolean testRookCastling(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
		
		
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] matrix = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0);
		
		// above
		p.setValues(position.getRow() - 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			matrix[p.getRow()][p.getColumn()] = true;
		}

		// below
		p.setValues(position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			matrix[p.getRow()][p.getColumn()] = true;
		}
		
		// left
		p.setValues(position.getRow(), position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			matrix[p.getRow()][p.getColumn()] = true;
		}		
		
		// right
		p.setValues(position.getRow(), position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			matrix[p.getRow()][p.getColumn()] = true;
		}		
		
		// nw
		p.setValues(position.getRow() - 1, position.getColumn() -1);
		if (getBoard().positionExists(p) && canMove(p)) {
			matrix[p.getRow()][p.getColumn()] = true;
		}			
		
		// ne
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			matrix[p.getRow()][p.getColumn()] = true;
		}	
		
		// sw
		p.setValues(position.getRow() + 1, position.getColumn() -1);
		if (getBoard().positionExists(p) && canMove(p)) {
			matrix[p.getRow()][p.getColumn()] = true;
		}
		
		// right
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			matrix[p.getRow()][p.getColumn()] = true;
		}
		
		// Special move castling
		
		if (getMoveCount() == 0 && !chessMatch.getCheck()) {
			// King side rook castling
			Position posKingRook = new Position(position.getRow(), position.getColumn() + 3);
			if (testRookCastling(posKingRook)) {
				Position aux1 = new Position(position.getRow(), position.getColumn() + 1);
				Position aux2 = new Position(position.getRow(), position.getColumn() + 2);
				if (getBoard().piece(aux1) == null && getBoard().piece(aux2) == null) {
					matrix[position.getRow()][position.getColumn() + 2] = true;
				}
			}
			
			// Queen side rook castling
			Position posQueenRook = new Position(position.getRow(), position.getColumn() - 4);
			if (testRookCastling(posQueenRook)) {
				Position aux1 = new Position(position.getRow(), position.getColumn() - 1);
				Position aux2 = new Position(position.getRow(), position.getColumn() - 2);
				Position aux3 = new Position(position.getRow(), position.getColumn() - 3);
				if (getBoard().piece(aux1) == null && getBoard().piece(aux2) == null && getBoard().piece(aux3) == null) {
					matrix[position.getRow()][position.getColumn() - 2] = true;
				}
			}
		}
		
		return matrix;
	}

}
