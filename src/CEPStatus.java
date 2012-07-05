public enum CEPStatus {
	YES_ONLY_POSTAGE("Possui apenas postagem e-Sedex"), YES("Possui postagem e entrega e-Sedex"), NO("N‹o possui postagem e nem entrega e-Sedex");

	private final String description;

	private CEPStatus(final String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return super.toString() + " - " + this.description;
	}
}
