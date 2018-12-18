package managers;

import enums.MoveAttribute;
import java.util.ArrayList;
import java.util.Collection;
import moves.Addition;
import moves.Move;
import regions.State;

/**
 *
 * @author Jayson
 */
public class UpdateManager {

  private Collection<Move> moves;
  private Collection<Addition> additions;
  private State state;
  private Integer currentSize;

  public UpdateManager(State state) {
    this.moves = new ArrayList<>();
    this.currentSize = 0;
    this.state = state;
  }

  public boolean isReady() {
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

  public void addAddition(Addition addition) {
    this.setCurrentSize(this.getCurrentSize() + addition.getSize());
    this.getAdditions().add(addition);
  }

  public Collection<Move> getMoves() {
    return this.moves;
  }

  public void setMoves(Collection<Move> moves) {
    this.moves = moves;
  }

  public State getState() {
    return this.state;
  }

  public Integer getCurrentSize() {
    return this.currentSize;
  }

  public void setCurrentSize(Integer value) {
    this.currentSize = value;
  }

  private Collection<Addition> getAdditions() {
    return this.additions;
  }

}
