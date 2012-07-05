import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ESedexCEPChecking {
	private static final int MINIMUM_LENGTH = 9 + 1 + 9 + 1;
	private final List<ESedexCEPChecking.CEPRange> cepRanges = new ArrayList<ESedexCEPChecking.CEPRange>();

	public ESedexCEPChecking() throws Exception {
		this.loadAllCEPRanges();
	}

	private void loadAllCEPRanges() throws Exception {
		final File ceps = new File(new File("."), "ceps.txt");
		final Scanner scanner = new Scanner(ceps);
		try {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine().trim();

				if (line.startsWith("#") || line.isEmpty() || line.length() < ESedexCEPChecking.MINIMUM_LENGTH) {
					continue;
				}

				final int lastCEP = Integer.parseInt(line.substring(line.lastIndexOf(" ") + 1, line.length()).replace("-", ""));
				line = line.substring(0, line.lastIndexOf(" "));

				final int priorLastCEP = Integer.parseInt(line.substring(line.lastIndexOf(" ") + 1, line.length()).replace("-", ""));

				final boolean onlyPostage = line.contains("*");

				this.cepRanges.add(new CEPRange(priorLastCEP, lastCEP, onlyPostage));
			}
		} catch (final Exception e) {
			throw e;
		} finally {
			scanner.close();
		}
	}

	public CEPStatus check(String stringCEP) {
		if (stringCEP == null) {
			throw new NullPointerException("stringCEP cannot be null");
		}
		stringCEP = stringCEP.trim().replace("-", "");
		if (stringCEP.length() != 8) {
			throw new IllegalArgumentException("invalid CEP '" + stringCEP + "'");
		}
		final int cep = Integer.parseInt(stringCEP);

		for (final CEPRange cepRange : this.cepRanges) {
			if (cepRange.isAvailable(cep)) {
				return cepRange.isOnlyPostage() ? CEPStatus.YES_ONLY_POSTAGE : CEPStatus.YES;
			}
		}
		return CEPStatus.NO;
	}

	private static class CEPRange {
		private final long min;
		private final long max;
		private final boolean onlyPostage;

		public CEPRange(final long min, final long max, final boolean onlyPostage) {
			this.min = min;
			this.max = max;
			this.onlyPostage = onlyPostage;
		}

		public boolean isAvailable(final int cep) {
			return cep >= this.min && cep <= this.max;
		}

		public boolean isOnlyPostage() {
			return this.onlyPostage;
		}
	}
}