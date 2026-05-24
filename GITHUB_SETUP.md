# GitHub Actions Setup

This workflow automatically builds your VerdantRealms mod every time you push code.

## How to Use

1. **Push this code to GitHub**
   ```bash
   git init
   git add .
   git commit -m "Initial VerdantRealms commit"
   git branch -M main
   git remote add origin https://github.com/YOUR_USERNAME/VerdantRealms.git
   git push -u origin main
   ```

2. **Go to your GitHub repository**
   - Navigate to the "Actions" tab
   - You should see the workflow running
   - Wait ~3-5 minutes for the build to complete

3. **Download your .jar file**
   - Go to the "Actions" tab
   - Click the latest completed workflow run
   - Scroll down to "Artifacts" section
   - Download `VerdantRealms-Mod` artifact (contains your .jar)

   OR

   - Go to "Releases" tab
   - Download from the "Development Build" release

## What the Workflow Does

- Sets up Java 21 (Temurin distribution)
- Caches Gradle dependencies for faster builds
- Compiles your mod with `./gradlew build`
- Uploads the `.jar` as a downloadable artifact
- Creates/updates a "latest" pre-release with the .jar attached

## Troubleshooting

**Build fails?**
- Check the Actions log for specific errors
- Common issues: missing textures, syntax errors in JSON files
- The log will show exactly which file failed

**Want to trigger a manual build?**
- Go to Actions → Build VerdantRealms Mod → Run workflow
- Click "Run workflow" button

## Customization

Edit `.github/workflows/build.yml` to:
- Change Java version
- Add tests
- Upload to CurseForge/Modrinth automatically
- Build on Windows/Mac in addition to Linux
