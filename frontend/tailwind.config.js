/** @type {import('tailwindcss').Config} */
export default {
  darkMode: "class",
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        bg: '#080D16',
        'bg-secondary': '#0D1420',
        'bg-tertiary': '#0A1018',
        panel: 'rgba(22, 34, 49, 0.45)',
        'glass-border': 'rgba(255, 255, 255, 0.08)',
        primary: '#54D7FF',
        'primary-dim': 'rgba(84, 215, 255, 0.15)',
        secondary: '#718CFF',
        success: '#39D9A0',
        'success-dim': 'rgba(57, 217, 160, 0.15)',
        warning: '#F4B860',
        'warning-dim': 'rgba(244, 184, 96, 0.15)',
        critical: '#FF536B',
        'critical-dim': 'rgba(255, 83, 107, 0.15)',
        'text-primary': '#EDF5FF',
        'text-secondary': '#91A3B8',
        'text-tertiary': '#5A6B7F',
      },
      fontFamily: {
        sans: ['Inter', 'system-ui', 'sans-serif'],
        mono: ['JetBrains Mono', 'monospace'],
      },
      backdropBlur: {
        xs: '2px',
        '2xl': '40px',
        '3xl': '64px',
      },
      boxShadow: {
        'glass': '0 8px 32px rgba(0, 0, 0, 0.3), inset 0 1px 0 rgba(255, 255, 255, 0.06)',
        'glass-lg': '0 16px 48px rgba(0, 0, 0, 0.4), inset 0 1px 0 rgba(255, 255, 255, 0.08)',
        'glow-primary': '0 0 20px rgba(84, 215, 255, 0.15), 0 0 40px rgba(84, 215, 255, 0.05)',
        'glow-success': '0 0 20px rgba(57, 217, 160, 0.15), 0 0 40px rgba(57, 217, 160, 0.05)',
        'glow-critical': '0 0 20px rgba(255, 83, 107, 0.15), 0 0 40px rgba(255, 83, 107, 0.05)',
      },
      animation: {
        'fade-in': 'fadeIn 0.3s ease-out forwards',
        'slide-in': 'slideIn 0.3s ease-out forwards',
        'pulse-glow': 'pulseGlow 2s ease-in-out infinite',
        'shimmer': 'shimmer 2s infinite',
        'float': 'float 3s ease-in-out infinite',
      },
      keyframes: {
        fadeIn: {
          '0%': { opacity: '0', transform: 'translateY(10px)' },
          '100%': { opacity: '1', transform: 'translateY(0)' },
        },
        slideIn: {
          '0%': { transform: 'translateX(-100%)', opacity: '0' },
          '100%': { transform: 'translateX(0)', opacity: '1' },
        },
        pulseGlow: {
          '0%, 100%': { boxShadow: '0 0 20px rgba(84, 215, 255, 0.2)' },
          '50%': { boxShadow: '0 0 40px rgba(84, 215, 255, 0.5)' },
        },
        shimmer: {
          '0%': { backgroundPosition: '-200% 0' },
          '100%': { backgroundPosition: '200% 0' },
        },
        float: {
          '0%, 100%': { transform: 'translateY(0)' },
          '50%': { transform: 'translateY(-5px)' },
        },
      },
    },
  },
  plugins: [],
}
