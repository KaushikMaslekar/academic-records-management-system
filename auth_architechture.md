┌─────────────────────────────────────────────────────────────────┐
│ CLIENT (Postman/Browser) │
└────────────────────────────┬────────────────────────────────────┘
│
▼
┌─────────────────────────────────────────────────────────────────┐
│ 1. SecurityFilterChain (SecurityConfig.java) │
│ ├─ Intercepts ALL incoming HTTP requests │
│ ├─ Applies security rules (permitAll, hasRole, etc.) │
│ └─ Enforces HTTP Basic Authentication │
└────────────────────────────┬────────────────────────────────────┘
│
┌────────┴────────┐
│ │
▼ ▼
┌──────────────────┐ ┌──────────────────┐
│ Public Endpoint │ │Protected Endpoint│
│ /api/auth/** │ │ /api/students/** │
│ (NO AUTH) │ │ (NEEDS AUTH) │
└────────┬─────────┘ └────────┬─────────┘
│ │
▼ ▼
┌──────────────────┐ ┌──────────────────────────┐
│ AuthController │ │ Spring Security triggers │
│ (Registration) │ │ Authentication Process │
└────────┬─────────┘ └────────┬─────────────────┘
│ │
▼ ▼
┌──────────────────┐ ┌───────────────────────────────┐
│ UserService │ │ DaoAuthenticationProvider │
│ │ │ (configured in SecurityConfig)│
└────────┬─────────┘ └────────┬──────────────────────┘
│ │
│ ▼
│ ┌─────────────────────────────────┐
│ │ CustomUserDetailsService │
│ │ (loads user from database) │
│ └────────┬────────────────────────┘
│ │
▼ ▼
┌──────────────────────────────────────────┐
│ UserRepository │
│ (MongoDB Interface) │
└────────┬─────────────────────────────────┘
│
▼
┌──────────────────┐
│ MongoDB │
│ users collection│
└──────────────────┘
