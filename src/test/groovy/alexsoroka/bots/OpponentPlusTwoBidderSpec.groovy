package alexsoroka.bots

import spock.lang.Specification
import spock.lang.Unroll

class OpponentPlusTwoBidderSpec extends Specification {

  OpponentPlusTwoBidder cut

  def setup() {
    cut = new OpponentPlusTwoBidder()
  }

  @Unroll
  def 'init should throw #exception when (quantity=#quantity, cash=#cash)'() {
    when:
    cut.init(quantity, cash)

    then:
    thrown(IllegalArgumentException.class)

    where:
    quantity | cash || exception
    -1       | -5   || IllegalArgumentException.class
    6        | -1   || IllegalArgumentException.class
    -10      | 1    || IllegalArgumentException.class
  }

  @Unroll
  def 'placeBid should return bid=#bid when bid is first and cash=#cash'() {
    given:
    cut.init(0, cash)

    when:
    int result = cut.placeBid()

    then:
    result == bid

    where:
    cash || bid
    50   || 2
    10   || 2
    2    || 2
    1    || 0
    0    || 0
  }

  @Unroll
  def 'placeBid should return bid=#bid when lastOpponentBid=#lastOpponentBid and cash=#cash'() {
    given:
    cut.init(0, cash)
    cut.bids(0, lastOpponentBid)

    when:
    int result = cut.placeBid()

    then:
    result == bid

    where:
    lastOpponentBid | cash || bid
    30              | 100  || 32
    49              | 51   || 51
    49              | 50   || 0
    50              | 50   || 0
    100             | 30   || 0
    0               | 0    || 0
  }

  @Unroll
  def 'bids should throw #exception when (own=#own, other=#other)'() {
    when:
    cut.bids(own, other)

    then:
    thrown(IllegalArgumentException.class)

    where:
    own | other || exception
    -5  | -5    || IllegalArgumentException.class
    5   | -5    || IllegalArgumentException.class
    -5  | 5     || IllegalArgumentException.class
  }
}
