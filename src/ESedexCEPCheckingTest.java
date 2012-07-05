import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ESedexCEPCheckingTest {

	private static ESedexCEPChecking cepChecking;

	@BeforeClass
	public static void createChecker() throws Exception {
		ESedexCEPCheckingTest.cepChecking = new ESedexCEPChecking();

		final CEPStatus cepStatus = ESedexCEPCheckingTest.cepChecking.check("19400000");
		System.out.println(cepStatus);
	}

	@Test
	public void firstCepFrom_PresidenteVenceslauShouldBeOnlyPostage() {
		final CEPStatus cepStatus = ESedexCEPCheckingTest.cepChecking.check("19400-000");
		Assert.assertEquals(CEPStatus.YES_ONLY_POSTAGE, cepStatus);
	}

	@Test
	public void lastCepFrom_PresidenteVenceslauShouldBeOnlyPostage() {
		final CEPStatus cepStatus = ESedexCEPCheckingTest.cepChecking.check("19409999");
		Assert.assertEquals(CEPStatus.YES_ONLY_POSTAGE, cepStatus);
	}

	@Test
	public void firstCepFrom_SaoPauloCapitalShouldBeYes() {
		final CEPStatus cepStatus = ESedexCEPCheckingTest.cepChecking.check("01000-000");
		Assert.assertEquals(CEPStatus.YES, cepStatus);
	}

	@Test
	public void lastCepFrom_SaoPauloCapitalShouldBeYes() {
		final CEPStatus cepStatus = ESedexCEPCheckingTest.cepChecking.check("09999999");
		Assert.assertEquals(CEPStatus.YES, cepStatus);
	}

	@Test
	public void unknownCEPShouldBeNO() {
		final CEPStatus cepStatus = ESedexCEPCheckingTest.cepChecking.check("65884-884");
		Assert.assertEquals(CEPStatus.NO, cepStatus);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldntAcceptCEPsGreaterThanNineCharacters() {
		ESedexCEPCheckingTest.cepChecking.check("48554-8745");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldntAcceptCEPsLowerThanEightCharacters() {
		ESedexCEPCheckingTest.cepChecking.check("48554-87");
	}

	@Test(expected = NullPointerException.class)
	public void shouldntAcceptNullCEP() {
		ESedexCEPCheckingTest.cepChecking.check(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldntAcceptBlankCEP() {
		ESedexCEPCheckingTest.cepChecking.check("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldntAcceptBlankNotTrimmedCEP() {
		ESedexCEPCheckingTest.cepChecking.check(" ");
	}

	@Test(expected = NumberFormatException.class)
	public void shouldntAcceptNonNumericCEP() {
		ESedexCEPCheckingTest.cepChecking.check("08o43546");
	}
}