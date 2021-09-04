
APP=base
TESTS="base lhi lhi2 lognosign reg imm mem"
TESTPATH=asm

hwsim:
	sbt -Dprogram=$(APP) "testOnly leros.LerosTest"


swsim:
	sbt -Dprogram=$(APP) "testOnly leros.sim.LerosSimSpec"

hw:
	sbt "runMain leros.Leros asm/$(APP).s"

test-alu:
	sbt "test:runMain leros.AluTester"

all: all-hwsim all-swsim

all-hwsim:
	sbt -Dtestpath=$(TESTPATH) -Dtests=$(TESTS) "testOnly leros.LerosTest"

all-swsim:
	sbt -Dtestpath=$(TESTPATH) -Dtests=$(TESTS) "testOnly leros.sim.LerosSimSpec"

# clean everything (including IntelliJ project settings)
clean:
	git clean -fd
