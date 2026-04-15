# TMDB Movie Database App — 改造計畫

## 目標
將 Playground 改造成一個可以在面試中展示的 TMDB 電影資料庫 Android App。

## 功能
- **首頁** — 熱門電影列表（無限滾動）
- **搜尋** — 關鍵字搜尋，RxJava debounce 防抖
- **電影詳情** — 海報、簡介、評分、收藏按鈕
- **收藏清單** — 本地 Room 儲存，離線可用

## 技術架構
| 技術 | 用途 |
|------|------|
| Retrofit2 + OkHttp3 | TMDB API 串接 |
| RxJava2 + RxKotlin | 非同步處理 |
| RxBinding | 搜尋框防抖 |
| Room | 收藏本地儲存 |
| Epoxy (Airbnb) | 電影列表渲染 |
| Navigation Component | Fragment 導航 |
| Dagger 2 | 依賴注入 |
| Glide | 圖片載入 |
| Timber | Log |
| ViewBinding | 取代 Butterknife |

## 模組邊界
- `repository` module → 資料層（API、Room、Repository）
- `app` module → UI 層（Fragment、ViewModel、Epoxy）

---

## 實作清單

### PHASE 1 — 清理 [ ]
- [ ] 刪除 Firebase 相關 feature（account、chat、signin）
- [ ] 刪除舊 feature（cityguide、eat、shop）
- [ ] 刪除舊 layouts、menus、drawables
- [ ] 刪除 Firebase utils
- [ ] 更新 `build.gradle`（root）— 移除 Firebase、Butterknife plugin
- [ ] 更新 `app/build.gradle` — 移除 Firebase、Butterknife；加 ViewBinding、ViewModel
- [ ] 更新 `repository/build.gradle` — 加 Retrofit、Room、TMDB token
- [ ] 更新 `local.properties` — 加 TMDB token（不進 git）
- [ ] 確認 `.gitignore` 有包含 `local.properties`
- [ ] 清理 `AndroidManifest.xml`
- [ ] 清理 `strings.xml`、`colors.xml`

### PHASE 2 — Repository Module [ ]
- [ ] `TmdbApiService.kt` — Retrofit interface（popular、search、detail）
- [ ] `MovieResponse.kt` — API response model
- [ ] `Movie.kt` — 電影資料 model
- [ ] `FavoriteEntity.kt` — Room entity
- [ ] `FavoriteDao.kt` — Room DAO（insert、delete、getAll、isFavorite）
- [ ] `MovieDatabase.kt` — Room database
- [ ] `MovieRepository.kt` — 統一資料入口（interface + impl）
- [ ] `RepositoryModule.kt` — Dagger module

### PHASE 3 — App DI [ ]
- [ ] `AppComponent.kt` — Dagger AppComponent（Singleton）
- [ ] `AppModule.kt` — 提供 OkHttpClient、Retrofit
- [ ] 更新 `MainApplication.kt` — 初始化 AppComponent

### PHASE 4 — Home [ ]
- [ ] `HomeFragment.kt`
- [ ] `HomeViewModel.kt` — 呼叫 popular movies API，分頁
- [ ] `MovieEpoxyModel.kt` — 電影卡片 Epoxy model
- [ ] `fragment_home.xml` — RecyclerView layout
- [ ] `item_movie.xml` — 電影卡片 layout

### PHASE 5 — Detail [ ]
- [ ] `DetailFragment.kt` — 接收 movieId，顯示詳情
- [ ] `DetailViewModel.kt` — 呼叫 detail API，收藏切換
- [ ] `fragment_detail.xml` — 詳情頁 layout

### PHASE 6 — Search [ ]
- [ ] `SearchFragment.kt` — RxBinding debounce 300ms
- [ ] `SearchViewModel.kt` — 呼叫 search API
- [ ] `fragment_search.xml` — 搜尋框 + 結果列表

### PHASE 7 — Favorite [ ]
- [ ] `FavoriteFragment.kt`
- [ ] `FavoriteViewModel.kt` — 從 Room 取收藏清單
- [ ] `fragment_favorite.xml`

### PHASE 8 — Navigation & MainActivity [ ]
- [ ] `main_navigation.xml` — 更新 Navigation graph
- [ ] `activity_main.xml` — Bottom Navigation（Home、Search、Favorite）
- [ ] `nav_menu.xml` — Bottom nav menu
- [ ] `MainActivity.kt` — 精簡為只負責 Navigation

### PHASE 9 — 收尾 [ ]
- [ ] 更新 `README.md`
- [ ] 確認 `local.properties` 在 `.gitignore`
- [ ] Commit & Push

---

## TMDB API
- Base URL: `https://api.themoviedb.org/3/`
- Image URL: `https://image.tmdb.org/t/p/w500`
- Auth: Bearer Token（存在 `local.properties`）
- 使用到的 endpoints:
  - `GET /movie/popular` — 熱門電影
  - `GET /search/movie?query=xxx` — 搜尋
  - `GET /movie/{id}` — 電影詳情
