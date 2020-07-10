package mk.ukim.finki.emt.lab.domain.financial;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for {@link Fee4K}.
 */
public class Fee4KTest {

    @Test
    public void valueOf_nullPercentage_nullReturned() {
        assertThat(Fee4K.valueOf(null)).isNull();
    }

    @Test
    public void valueOf_nonNullPercentage_vatCreated() {
        assertThat(Fee4K.valueOf(24)).isNotNull();
    }

    @Test
    public void addTax_noDecimals() {
        var withTax = new Fee4K(24).addTax(new Money(Currency.EUR, 100.00));
        assertThat(withTax).isEqualTo(new Money(Currency.EUR, 124.00));
    }

    @Test
    public void addTax_withDecimals() {
        var withTax = new Fee4K(24).addTax(new Money(Currency.EUR, 1.00));
        assertThat(withTax).isEqualTo(new Money(Currency.EUR, 1.24));
    }

    @Test
    public void subtractTax_noDecimals() {
        var withoutTax = new Fee4K(24).subtractTax(new Money(Currency.EUR, 124.00));
        assertThat(withoutTax).isEqualTo(new Money(Currency.EUR, 100.00));
    }

    @Test
    public void subtractTax_withDecimals() {
        var withoutTax = new Fee4K(24).subtractTax(new Money(Currency.EUR, 1.24));
        assertThat(withoutTax).isEqualTo(new Money(Currency.EUR, 1.00));
    }

    @Test
    public void toDouble_returnedAsFraction() {
        assertThat(new Fee4K(24).toDouble()).isEqualTo(0.24);
    }

    @Test
    public void toInteger_returnedAsPercent() {
        assertThat(new Fee4K(24).toInteger()).isEqualTo(24);
    }

    @Test
    public void toString_returnedAsFormattedString() {
        assertThat(new Fee4K(24).toString()).isEqualTo("24 %");
    }
}
