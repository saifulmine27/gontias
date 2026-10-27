# ঘণ্টি (Ghonti) – Reminder অ্যাপ

ওয়েব অ্যাপের ডিজাইন + নেটিভ Android অ্যালার্ম। অ্যাপ বন্ধ বা ফোন লক থাকলেও অ্যালার্ম বাজে।

## GitHub দিয়ে APK বানানো (কম্পিউটারে কিছু ইনস্টল না করেই)
1. GitHub-এ নতুন repository বানান (যেমন `ghonti`)।
2. এই ফোল্ডারের **সব ফাইল** (`.github` ফোল্ডারসহ) repository-তে আপলোড করুন।
   - ওয়েবে: **Add file → Upload files** → সব ফাইল/ফোল্ডার টেনে দিন → **Commit changes**।
   - `.github` ফোল্ডার লুকানো থাকে, ফাইল ম্যানেজারে "hidden files" চালু করে নিন।
3. repository-র **Actions** ট্যাবে যান। "Build APK" নিজে থেকে চালু হবে (না হলে বাঁদিকে **Build APK → Run workflow**)।
4. ৩-৫ মিনিট পর সবুজ টিক এলে ওই run-এ ক্লিক করুন।
5. একদম নিচে **Artifacts → ghonti-apk** চাপলে zip নামবে। তার ভেতরে `app-debug.apk`।
6. APK ফোনে পাঠিয়ে ইনস্টল করুন (Settings-এ "Install unknown apps" চালু করুন)।

## ফোনে দরকারি সেটিং
- প্রথমবার notification permission **Allow** করুন।
- Settings → Apps → ঘণ্টি → Battery → **Unrestricted** করুন, না হলে কিছু ফোনে অ্যালার্ম দেরিতে বাজতে পারে।

## UI বদলাতে চাইলে
`app/src/main/assets/index.html` এডিট করে commit করুন, নতুন APK নিজে থেকে বিল্ড হবে।

## ফাইল কাঠামো
- `app/src/main/assets/index.html` — ওয়েব অ্যাপ
- `app/src/main/java/com/ghonti/app/` — অ্যালার্ম সিডিউল ও নোটিফিকেশন (Kotlin)
- `app/src/main/res/raw/tone_*.wav` — ৭টা রিংটোন
- `.github/workflows/build-apk.yml` — অটো বিল্ড
