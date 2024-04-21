🖼️ Image Loader App

Overview
This Android application 📱 is designed to efficiently load and display images in a scrollable grid. The app utilizes native technologies (Kotlin) to implement image loading, caching mechanisms, and error handling as per the provided assignment guidelines.

Tasks

Image Grid: 🖼️ The app presents a 3-column square image grid, center-cropped for optimal display.
Image Loading: ⏳ Asynchronous image loading is implemented using a provided API. The constructed image URLs follow a specific formula using fields from the API response.
Caching: 💾 The app incorporates caching mechanisms to store images retrieved from the API in both memory and disk cache for efficient retrieval.
Error Handling: ⚠️ Network errors and image loading failures are gracefully handled, providing informative error messages or placeholders for failed image loads.

Implementation Details

Language: Kotlin
Technologies: Native Android
Dependencies: No third-party image loading libraries are used.

Usage Instructions

Clone the repository from GitHub: ImageLoader Repository
Open the project in Android Studio.
Build and run the project on an Android device or emulator.
Navigate through the image grid and observe smooth scrolling and efficient loading.

Additional Notes

Both memory and disk cache mechanisms are implemented, with disk cache utilized when an image is missing from memory cache. Memory cache is updated when an image is read from disk.

For any queries or clarifications, please contact: mailshubham8995@gmail.com.
