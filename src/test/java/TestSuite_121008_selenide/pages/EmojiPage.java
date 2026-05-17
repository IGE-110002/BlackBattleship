package TestSuite_121008_selenide.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class EmojiPage {

    private final WebDriver driver;

    public EmojiPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Opens the emoji/reaction menu.
     */
    public void openEmojiMenu() throws InterruptedException {
        Thread.sleep(3000);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        Object result = js.executeScript("""
            function isVisible(el) {
                const rect = el.getBoundingClientRect();
                const style = window.getComputedStyle(el);

                return rect.width > 0 &&
                       rect.height > 0 &&
                       style.display !== 'none' &&
                       style.visibility !== 'hidden' &&
                       style.opacity !== '0';
            }

            const buttons = Array.from(document.querySelectorAll(
                'button, [role="button"]'
            ));

            let emojiButton = null;

            // Try to find emoji/reaction button
            for (const btn of buttons) {

                if (!isVisible(btn)) continue;

                const text = btn.textContent.toLowerCase();
                const html = btn.innerHTML.toLowerCase();
                const cls = btn.className.toString().toLowerCase();
                const id = btn.id.toLowerCase();

                if (
                    text.includes('emoji') ||
                    text.includes('reaction') ||
                    html.includes('emoji') ||
                    html.includes('reaction') ||
                    html.includes('smile') ||
                    cls.includes('emoji') ||
                    cls.includes('reaction') ||
                    id.includes('emoji') ||
                    id.includes('reaction')
                ) {
                    emojiButton = btn;
                    break;
                }
            }

            // Fallback: use last visible button
            if (!emojiButton) {

                const visibleButtons = buttons.filter(btn => isVisible(btn));

                if (visibleButtons.length > 0) {
                    emojiButton = visibleButtons[visibleButtons.length - 1];
                }
            }

            if (!emojiButton) {
                return {
                    success: false,
                    message: 'Emoji button not found'
                };
            }

            emojiButton.click();

            return {
                success: true
            };
        """);

        if (result instanceof java.util.Map) {

            @SuppressWarnings("unchecked")
            java.util.Map<String, Object> resultMap =
                    (java.util.Map<String, Object>) result;

            if ((boolean) resultMap.get("success")) {

                System.out.println("✅ Emoji menu opened successfully");
                Thread.sleep(1500);
                return;
            }

            System.out.println("❌ " + resultMap.get("message"));
        }

        throw new IllegalStateException(
                "Could not open emoji menu."
        );
    }

    /**
     * Selects and sends an emoji reaction automatically.
     */
    public void sendEmoji() throws InterruptedException {

        Thread.sleep(1500);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        Object result = js.executeScript("""
            function isVisible(el) {

                const rect = el.getBoundingClientRect();
                const style = window.getComputedStyle(el);

                return rect.width > 0 &&
                       rect.height > 0 &&
                       style.display !== 'none' &&
                       style.visibility !== 'hidden' &&
                       style.opacity !== '0';
            }

            function clickElement(el) {

                el.scrollIntoView({
                    block: 'center',
                    inline: 'center'
                });

                const rect = el.getBoundingClientRect();

                const x = rect.left + rect.width / 2;
                const y = rect.top + rect.height / 2;

                el.dispatchEvent(new MouseEvent('mouseover', {
                    bubbles: true,
                    clientX: x,
                    clientY: y
                }));

                el.dispatchEvent(new MouseEvent('mousedown', {
                    bubbles: true,
                    clientX: x,
                    clientY: y
                }));

                el.dispatchEvent(new MouseEvent('mouseup', {
                    bubbles: true,
                    clientX: x,
                    clientY: y
                }));

                el.dispatchEvent(new MouseEvent('click', {
                    bubbles: true,
                    clientX: x,
                    clientY: y
                }));
            }

            let candidates = Array.from(document.querySelectorAll(
                'button, [role="button"], img, svg, span, div'
            )).filter(el => {

                if (!isVisible(el)) return false;

                const text = el.textContent.trim().toLowerCase();
                const html = el.outerHTML.toLowerCase();
                const cls = el.className.toString().toLowerCase();
                const id = el.id.toLowerCase();

                return (
                    text.match(/[😀-🙏🌀-🗿🚀-🛿☀-⛿✀-➿]/u) ||
                    html.includes('emoji') ||
                    html.includes('reaction') ||
                    html.includes('emote') ||
                    html.includes('smile') ||
                    html.includes('face') ||
                    cls.includes('emoji') ||
                    cls.includes('reaction') ||
                    cls.includes('emote') ||
                    id.includes('emoji') ||
                    id.includes('reaction') ||
                    id.includes('emote')
                );
            });

            // Fallback search
            if (candidates.length === 0) {

                candidates = Array.from(document.querySelectorAll(
                    'button, [role="button"], img, svg'
                )).filter(el => {

                    if (!isVisible(el)) return false;

                    const rect = el.getBoundingClientRect();

                    return rect.width >= 16 &&
                           rect.height >= 16 &&
                           rect.width <= 80 &&
                           rect.height <= 80;
                });
            }

            if (candidates.length === 0) {

                return {
                    success: false,
                    step: 'select emoji',
                    message: 'No clickable emoji/reaction found'
                };
            }

            // Use last candidate
            const emojiOption = candidates[candidates.length - 1];

            const selectedText =
                emojiOption.textContent.trim() || 'emoji';

            clickElement(emojiOption);

            return {
                success: true,
                emoji: selectedText,
                candidatesFound: candidates.length
            };
        """);

        if (result instanceof java.util.Map) {

            @SuppressWarnings("unchecked")
            java.util.Map<String, Object> resultMap =
                    (java.util.Map<String, Object>) result;

            if ((boolean) resultMap.get("success")) {

                System.out.println(
                        "✅ Emoji reaction sent successfully: "
                                + resultMap.get("emoji")
                );

                System.out.println(
                        "Candidates found: "
                                + resultMap.get("candidatesFound")
                );

                Thread.sleep(2000);
                return;
            }

            System.out.println(
                    "❌ Failed at step: "
                            + resultMap.get("step")
            );

            System.out.println(
                    "❌ " + resultMap.get("message")
            );
        }

        throw new IllegalStateException(
                "Could not send emoji reaction."
        );
    }
}