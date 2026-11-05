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
        bg: "#080D16",
        "bg-secondary": "#0D1420",
        panel: "rgba(22, 34, 49, 0.72)",
        "glass-border": "rgba(255, 255, 255, 0.10)",
        primary: "#54D7FF",
        secondary: "#718CFF",
        success: "#39D9A0",
        warning: "#F4B860",
        critical: "#FF536B",
        "text-primary": "#EDF5FF",
        "text-secondary": "#91A3B8",
      },
      fontFamily: {
        sans: ["Inter", "system-ui", "sans-serif"],
        mono: ["JetBrains Mono", "monospace"],
      },
      backdropBlur: {
        xs: "2px",
      },
      animation: {
        "fade-in": "fadeIn 0.3s ease-out forwards",
        "slide-in": "slideIn 0.3s ease-out forwards",
        "pulse-glow": "pulseGlow 2s ease-in-out infinite",
      },
      keyframes: {
        fadeIn: {
          "0%": { opacity: "0" },
          "100%": { opacity: "1" },
        },
        slideIn: {
          "0%": { transform: "translateX(-100%)", opacity: "0" },
          "100%": { transform: "translateX(0)", opacity: "1" },
        },
        pulseGlow: {
          "0%, 100%": { boxShadow: "0 0 20px rgba(84, 215, 255, 0.2)" },
          "50%": { boxShadow: "0 0 40px rgba(84, 215, 255, 0.5)" },
        },
      },
    },
  },
  plugins: [],
}
