document.addEventListener('DOMContentLoaded', () => {
	const passwordInput = document.querySelector('[data-password-strength]');
	const meterBar = document.querySelector('[data-password-meter]');
	const meterText = document.querySelector('[data-password-strength-text]');

	if (!passwordInput || !meterBar || !meterText) {
		return;
	}

	const updateStrength = () => {
		const value = passwordInput.value || '';
		let score = 0;

		if (value.length >= 8) score += 1;
		if (/[A-Z]/.test(value)) score += 1;
		if (/[a-z]/.test(value)) score += 1;
		if (/[0-9]/.test(value)) score += 1;
		if (/[^A-Za-z0-9]/.test(value)) score += 1;

		const width = Math.min(score * 20, 100);
		meterBar.style.width = width + '%';

		if (score <= 1) {
			meterText.textContent = 'Weak';
			meterText.className = 'small text-danger fw-semibold';
		} else if (score <= 3) {
			meterText.textContent = 'Medium';
			meterText.className = 'small text-warning fw-semibold';
		} else {
			meterText.textContent = 'Strong';
			meterText.className = 'small text-success fw-semibold';
		}
	};

	passwordInput.addEventListener('input', updateStrength);
	updateStrength();
});
