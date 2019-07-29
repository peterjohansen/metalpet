package org.pemacy.metalpet.service.output;

import org.springframework.stereotype.Service;

@Service
public class OutputService {

	public void print(Object obj) {
		System.out.print(obj);
	}

	public void println() {
		println("");
	}

	public void println(Object obj) {
		print(obj);
		print("\n");
	}

	public void printf(String messageFormat, Object... args) {
		print(String.format(messageFormat, args));
	}

	public void printfln(String messageFormat, Object... args) {
		printf(messageFormat, args);
		println();
	}

}
