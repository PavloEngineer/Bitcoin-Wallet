# 💰 Bitcoin-Wallet

**Bitcoin-Wallet** is a simple Android app for tracking personal expenses in Bitcoin.  
It allows users to top up their BTC balance, add categorized expense transactions, and view all operations grouped by date.

The BTC-to-USD exchange rate is fetched from the public [CoinGecko API](https://api.coingecko.com/api/v3) and cached for up to **1 hour** per app session.

---

## 🚀 Features

- 📈 Display of current Bitcoin balance
- ➕ Balance top-up via dialog
- 🗂️ Grouped transaction history by date (income + expenses)
- 🔁 **Pagination**: loads 20 transactions per page while scrolling
- 💸 Add transaction screen with category selection:
  - `groceries`, `taxi`, `electronics`, `restaurant`, `other`
- 💵 Real-time BTC → USD exchange rate (cached per session)

---

## 🛠 Tech Stack

- [Kotlin](https://kotlinlang.org/)
- [Android SDK](https://developer.android.com/)
- [Jetpack Compose](https://developer.android.com/jetpack/compose) – Declarative UI
- [Room](https://developer.android.com/training/data-storage/room) – Local database
- [Hilt](https://dagger.dev/hilt/) – Dependency Injection
- [Retrofit](https://square.github.io/retrofit/) – Networking
- [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) – Asynchronous logic
- [CoinGecko API](https://api.coingecko.com/api/v3) – Exchange rate data

---

## 🧱 Architecture

This project follows a **Clean Architecture** approach, separating responsibilities into:

- `UI` layer – Jetpack Compose with state management
- `Domain` layer – UseCases and business logic
- `Data` layer – Room DB and Retrofit networking

---

## 📷 Demo

https://github.com/user-attachments/assets/dd9d6c8f-d54c-4d26-bb3b-6b3a903a07e7


