package managers;

import enums.MoveAttribute;
import java.util.ArrayList;
import java.util.Collection;
import moves.Move;

/**
 *
 * @author Jayson
 */
public class UpdateManager {

  private Collection<Move> moves;
  private Integer currentSize;
  private Boolean completetionStatus;

  public UpdateManager() {
    this.moves = new ArrayList<>();
    this.currentSize = 0;
    this.completetionStatus = false;
  }

  public Boolean isReady() {
    return this.getCurrentSize() >= MoveAttribute.MAX_CAPACITY.getValue();
  }

  public void reset() {
    this.setCurrentSize(0);
    this.setMoves(new ArrayList<>());
  }

  public void addMove(Move move) {
    this.setCurrentSize(this.getCurrentSize() + move.getSize());
    this.getMoves().add(move);
  }

  public Collection<Move> getMoves() {
    return this.moves;
  }

  public void setMoves(Collection<Move> moves) {
    this.moves = moves;
  }

  public Integer getCurrentSize() {
    return this.currentSize;
  }

  public void setCurrentSize(Integer value) {
    this.currentSize = value;
  }

  public Boolean isComplete() {
    return this.completetionStatus;
  }

  public void setComplete(Boolean status) {
    this.completetionStatus = status;
  }

}
