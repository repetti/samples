// Other files can be also included using:
// include "included.thrift"

namespace java org.repetti.thrift.samples.trift.generated

enum Result {
  SUCCESS = 1,
  FAIL = 2
}

struct SpaceMonkey {
  1: string name,
  2: i32 weight,
  3: i32 age = 0,
  4: bool justCame = false,
  5: optional string description
}

struct Response {
  1: string msg,
  2: Result result
}

const i32 MYFAVOURITECONSTANT = 1996
const map<string,string> MAPCONSTANT = {
    'worker bees':'can leave',
    'even drones':'can fly away',
    'the queen':'is their slave'
}

exception BadMonkeyException {
  1: string description
}

service BasicService {
   /* oneway = always void and doesn't require any answer */
    oneway void knockKnock()
}

service ExtendedService extends BasicService {

    i32 sum(1:i32 first, 2:i32 second),

    Response inspect(1:SpaceMonkey candidate) throws (1:BadMonkeyException err)
}