//
// Swiss QR Bill Generator
// Copyright (c) 2017 Manuel Bleichenbacher
// Licensed under MIT License
// https://opensource.org/licenses/MIT
//
package net.codecrete.qrbill.generator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

/**
 * QR bill data
 */
public class Bill implements Serializable {

    private static final long serialVersionUID = -8104086304378262190L;

    /**
     * QR bill version
     */
    public enum Version {
        /**
         * Version 2.0
         */
        V2_0
    }

    private Version version = Version.V2_0;
    private BigDecimal amount = null;
    private String currency = "CHF";
    private String account = null;
    private Address creditor = new Address();
    private String reference = null;
    private Address debtor = null;
    private String unstructuredMessage = null;
    private String billInformation = null;
    private AlternativeScheme[] alternativeSchemes = null;
    private BillFormat format = new BillFormat();

    /**
     * Gets the version of the QR bill standard.
     *
     * @return the version
     */
    public Version getVersion() {
        return version;
    }

    /**
     * Sets the version of the QR bill standard.
     *
     * @param version the version
     */
    public void setVersion(Version version) {
        this.version = version;
    }

    /**
     * Gets the payment amount
     *
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Gets the payment amount as a {@code Double} instance
     *
     * @return the amount
     */
    public Double getAmountAsDouble() {
        if (amount != null)
            return amount.doubleValue();
        else
            return null;
    }

    /**
     * Sets the payment amount.
     * <p>
     * Valid values are between 0.01 and 999,999,999.99
     * </p>
     *
     * @param amount the amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Sets the payment amount from a {@code Double} solid-line-with_scissors
     * <p>
     * The value is saved with a scale of 2.
     * </p>
     *
     * @param amount the amount
     */
    public void setAmountFromDouble(Double amount) {
        if (amount != null)
            this.amount = BigDecimal.valueOf((long) (amount * 100 + 0.5), 2);
        else
            this.amount = null;
    }

    /**
     * Gets the payment currency.
     *
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the payment currency.
     * <p>
     * Valid values are "CHF" and "EUR".
     * </p>
     *
     * @param currency the currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Gets the creditor's account number.
     *
     * @return the account number
     */
    public String getAccount() {
        return account;
    }

    /**
     * Sets the creditor's account number.
     * <p>
     * Account numbers must be valid IBANs of a bank of Switzerland or
     * Liechtenstein. Spaces are allowed in the account number.
     * </p>
     *
     * @param account the account number
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * Gets the creditor address.
     *
     * @return the creditor address
     */
    public Address getCreditor() {
        return creditor;
    }

    /**
     * Sets the creditor address.
     *
     * @param creditor the creditor address.
     */
    public void setCreditor(Address creditor) {
        this.creditor = creditor;
    }

    /**
     * Gets the payment reference
     *
     * @return the reference
     */
    public String getReference() {
        return reference;
    }

    /**
     * Sets the payment reference.
     * <p>
     * The reference is mandatory for QR IBANs, i.e. IBANs in the range
     * CHxx30000xxxxxx through CHxx31999xxxxx.
     * </p>
     * <p>
     * If specified, the reference must be either a valid QR reference (which
     * corresponds to the form ISR reference) or a valid creditor reference
     * according to ISO 11649 ("RFxxxx"). Both may contain spaces for formatting.
     * </p>
     *
     * @param reference the payment reference number
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * Creates and sets a ISO11649 creditor reference from a raw string by prefixing
     * the String with "RF" and the modulo 97 checksum.
     * <p>
     * Whitespace is removed from the reference
     * </p>
     *
     * @param rawReference The raw string
     * @throws IllegalArgumentException if {@code rawReference} contains invalid
     *                                  characters
     */
    public void createAndSetCreditorReference(String rawReference) {
        setReference(Payments.createISO11649Reference(rawReference));
    }

    /**
     * Gets the debtor's address.
     *
     * @return the debtor address
     */
    public Address getDebtor() {
        return debtor;
    }

    /**
     * Sets the debtor's address.
     * <p>
     * The debtor is optional. If it is omitted, both setting this field to
     * {@code null} or setting an address with all {@code null} or empty values is
     * ok.
     * </p>
     *
     * @param debtor the debtor address
     */
    public void setDebtor(Address debtor) {
        this.debtor = debtor;
    }

    /**
     * Gets the additional unstructured message.
     *
     * @return the unstructured message
     */
    public String getUnstructuredMessage() {
        return unstructuredMessage;
    }

    /**
     * Sets the additional unstructured message.
     *
     * @param unstructuredMessage the unstructured message
     */
    public void setUnstructuredMessage(String unstructuredMessage) {
        this.unstructuredMessage = unstructuredMessage;
    }

    /**
     * Gets the additional bill information.
     *
     * @return bill information
     */
    public String getBillInformation() {
        return billInformation;
    }

    /**
     * Sets the additional bill information
     *
     * @param billInformation bill information
     */
    public void setBillInformation(String billInformation) {
        this.billInformation = billInformation;
    }

    /**
     * Get the alternative schemes.
     * <p>
     * A maximum of two schemes are allowed.
     * </p>
     *
     * @return alternative schemes
     */
    public AlternativeScheme[] getAlternativeSchemes() {
        return alternativeSchemes;
    }

    /**
     * Sets the alternative scheme parameters.
     * <p>
     * A maximum of two schemes with parameters are allowed.
     * </p>
     *
     * @param alternativeSchemes alternative payment scheme information
     */
    public void setAlternativeSchemes(AlternativeScheme[] alternativeSchemes) {
        this.alternativeSchemes = alternativeSchemes;
    }

    /**
     * Gets the bill format.
     *
     * @return bill format
     */
    public BillFormat getFormat() {
        return format;
    }

    /**
     * Sets the bill format.
     *
     * @param format bill format
     */
    public void setFormat(BillFormat format) {
        this.format = format;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill = (Bill) o;
        return version == bill.version &&
                Objects.equals(amount, bill.amount) &&
                Objects.equals(currency, bill.currency) &&
                Objects.equals(account, bill.account) &&
                Objects.equals(creditor, bill.creditor) &&
                Objects.equals(reference, bill.reference) &&
                Objects.equals(debtor, bill.debtor) &&
                Objects.equals(unstructuredMessage, bill.unstructuredMessage) &&
                Objects.equals(billInformation, bill.billInformation) &&
                Arrays.equals(alternativeSchemes, bill.alternativeSchemes) &&
                Objects.equals(format, bill.format);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {

        int result = Objects.hash(version, amount, currency, account, creditor, reference, debtor, unstructuredMessage, billInformation, format);
        result = 31 * result + Arrays.hashCode(alternativeSchemes);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Bill{" +
                "version=" + version +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", account='" + account + '\'' +
                ", creditor=" + creditor +
                ", reference='" + reference + '\'' +
                ", debtor=" + debtor +
                ", unstructuredMessage='" + unstructuredMessage + '\'' +
                ", billInformation='" + billInformation + '\'' +
                ", alternativeSchemes=" + Arrays.toString(alternativeSchemes) +
                ", format=" + format +
                '}';
    }
}
