package org.pemacy.metalpet.service.output;

@FunctionalInterface
public interface OutputService {

	void print(Object obj);

	default void println() {
		println("");
	}

	default void println(Object obj) {
		print(obj);
		print("\n");
	}

	default void printf(String messageFormat, Object... args) {
		print(String.format(messageFormat, args));
	}

	default void printfln(String messageFormat, Object... args) {
		printf(messageFormat, args);
		println();
	}

}
