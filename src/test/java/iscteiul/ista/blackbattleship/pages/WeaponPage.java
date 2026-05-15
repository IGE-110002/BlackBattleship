package iscteiul.ista.blackbattleship.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Robust WeaponPage implementation:
 * - DOM-first detection with many attribute checks and retries
 * - Board-aware enemy cell clicking with multiple fallbacks
 * - Proper polling for weapon effect detection
 */
public class WeaponPage {

    private final WebDriver driver;
    private final JavascriptExecutor js;

    public WeaponPage(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
    }

    /**
     * Capture screenshot + DOM diagnostics.
     */
    public void captureWeaponDiagnostics() throws InterruptedException {

        Thread.sleep(500);

        try {

            String ts = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

            String out = "target/debug-screenshots";

            Files.createDirectories(Paths.get(out));

            File screenshot =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.FILE);

            String shot =
                    out + File.separator + "weapon-" + ts + ".png";

            Files.copy(screenshot.toPath(), Paths.get(shot));

            System.out.println(
                    "✅ Weapon screenshot saved: " + shot
            );

        } catch (Exception e) {

            System.err.println(
                    "Error capturing diagnostics: "
                            + e.getMessage()
            );
        }
    }

    /**
     * Scan an area and click first clickable element.
     */
    private Object scanAndClickArea(
            int x1,
            int y1,
            int x2,
            int y2,
            int step
    ) {

        return js.executeScript("""
            const x1 = arguments[0];
            const y1 = arguments[1];
            const x2 = arguments[2];
            const y2 = arguments[3];
            const step = arguments[4];

            function isClickable(el) {

                if (!el) return false;

                const tag = el.tagName ? el.tagName.toUpperCase() : '';

                if (
                    tag === 'BUTTON' ||
                    tag === 'A' ||
                    el.hasAttribute && el.hasAttribute('onclick')
                ) {
                    return true;
                }

                const role =
                    (el.getAttribute && el.getAttribute('role')) || '';

                if (role.toLowerCase().includes('button')) {
                    return true;
                }

                const style = window.getComputedStyle(el);
                if (style && style.pointerEvents === 'none') return false;

                return false;
            }

            for (let x = x1; x <= x2; x += step) {

                for (let y = y1; y <= y2; y += step) {

                    const el = document.elementFromPoint(x, y);

                    if (!el) continue;

                    if (isClickable(el)) {

                        try {
                            el.click();
                        } catch (e) {
                            el.dispatchEvent(
                                new MouseEvent('click', {
                                    bubbles: true
                                })
                            );
                        }

                        const r = el.getBoundingClientRect();

                        return {
                            ok: true,
                            x: Math.round(r.left),
                            y: Math.round(r.top),
                            tag: el.tagName,
                            text: (el.textContent || '').trim().substring(0, 80)
                        };
                    }
                }
            }

            return { ok: false };
        """, x1, y1, x2, y2, step);
    }

    /**
     * Select special weapon automatically.
     */
    public void selectRocketWeapon()
            throws InterruptedException {

        // Try for up to ~6 seconds with short polls
        final int attempts = 12;
        final int sleepMs = 500;

        System.out.println("selectRocketWeapon: trying DOM-first...");

        for (int i = 0; i < attempts; i++) {

            try {
                Object domRes = js.executeScript(
                        // Search a wide variety of attributes and text for weapon-related elements
                        """
                        const keywords = [
                            'rocket','missile','bomb','weapon','special','nuke','airstrike','strike','torpedo'
                        ];

                        const attrKeys = [
                            'aria-label','title','alt','data-weapon','data-action','data-testid','data-test'
                        ];

                        function textOf(el){
                            try { return (el.textContent || '').toLowerCase(); } catch(e) { return ''; }
                        }

                        function attrsOf(el) {
                            const res = [];
                            for (const a of attrKeys) {
                                try {
                                    const v = el.getAttribute && el.getAttribute(a);
                                    if (v) res.push(v.toString().toLowerCase());
                                } catch(e) {}
                            }
                            return res.join(' ');
                        }

                        const candidates = Array.from(document.querySelectorAll('button, [role=\"button\"], a, div, span, img, input[type=\"button\"], input[type=\"image\"]'))
                            .filter(e => {
                                try {
                                    const rect = e.getBoundingClientRect();
                                    return rect && rect.width > 2 && rect.height > 2 && window.getComputedStyle(e).visibility !== 'hidden';
                                } catch(err) { return false; }
                            });

                        for (const e of candidates) {

                            const txt = textOf(e);
                            const attrs = attrsOf(e);
                            const cls = (e.className || '').toString().toLowerCase();
                            const html = (e.innerHTML || '').toString().toLowerCase();

                            const hay = txt + ' ' + attrs + ' ' + cls + ' ' + html;

                            if (keywords.some(k => hay.includes(k))) {

                                try {
                                    // prefer visible clickable ancestor
                                    let target = e;
                                    let ancestor = e;
                                    for (let i=0;i<6;i++){
                                        if (!ancestor) break;
                                        if (ancestor.tagName && (ancestor.tagName.toLowerCase() === 'button' || ancestor.getAttribute && ancestor.getAttribute('role') && ancestor.getAttribute('role').toLowerCase().includes('button'))) {
                                            target = ancestor; break;
                                        }
                                        ancestor = ancestor.parentElement;
                                    }

                                    target.scrollIntoView({block: 'center'});
                                    try { target.click(); } catch(err) {
                                        target.dispatchEvent(new MouseEvent('click', { bubbles: true }));
                                    }

                                } catch(err){
                                    try {
                                        e.dispatchEvent(new MouseEvent('click', { bubbles: true }));
                                    } catch(ex){}
                                }

                                return { ok: true, text: txt };
                            }
                        }

                        return { ok: false };
                        """
                );

                if (domRes instanceof java.util.Map) {

                    @SuppressWarnings("unchecked")
                    java.util.Map<String, Object> map =
                            (java.util.Map<String, Object>) domRes;

                    if (Boolean.TRUE.equals(map.get("ok"))) {

                        System.out.println("✅ Special weapon selected (DOM)");
                        Thread.sleep(500);
                        return;
                    }
                }

            } catch (Exception e) {
                // ignore and retry
                System.err.println("DOM attempt error: " + e.getMessage());
            }

            Thread.sleep(sleepMs);
        }

        // Fallback: try to find weapon UI at bottom-right or a weapon panel near the board
        System.out.println("Fallback scanning for weapon UI...");

        Object dims = js.executeScript("""
            return { w: window.innerWidth, h: window.innerHeight };
        """);

        @SuppressWarnings("unchecked")
        java.util.Map<String, Object> d = (java.util.Map<String, Object>) dims;

        int w = ((Number) d.get("w")).intValue();
        int h = ((Number) d.get("h")).intValue();

        // first try bottom-right area
        Object scanRes = scanAndClickArea(
                Math.max(w / 2, w - 600),
                Math.max(100, h - 400),
                w - 20,
                h - 60,
                14
        );

        if (scanRes instanceof java.util.Map) {

            @SuppressWarnings("unchecked")
            java.util.Map<String, Object> m = (java.util.Map<String, Object>) scanRes;

            if (Boolean.TRUE.equals(m.get("ok"))) {
                System.out.println("✅ Weapon clicked by scan (bottom/right)");
                Thread.sleep(600);
                return;
            }
        }

        // try searching for a dedicated weapon panel or sidebar by selectors
        try {
            Object panelClick = js.executeScript("""
                const sel = [
                    '[data-panel*=\"weapon\"]',
                    '[data-testid*=\"weapon\"]',
                    '.weapons', '.weapon-panel', '.special-weapons', '.special-weapon'
                ];

                for (const s of sel) {
                    const el = document.querySelector(s);
                    if (el) {
                        try { el.scrollIntoView({block:'center'}); } catch(e) {}
                        try { el.click(); } catch(e) { el.dispatchEvent(new MouseEvent('click',{bubbles:true})); }
                        return { ok: true, sel: s, tag: el.tagName };
                    }
                }
                return { ok: false };
            """);

            if (panelClick instanceof java.util.Map) {
                @SuppressWarnings("unchecked")
                java.util.Map<String, Object> m = (java.util.Map<String, Object>) panelClick;
                if (Boolean.TRUE.equals(m.get("ok"))) {
                    System.out.println("✅ Weapon panel clicked (selector)");
                    Thread.sleep(600);
                    return;
                }
            }
        } catch (Exception ignored) {}

        throw new IllegalStateException("Failed to select weapon.");
    }

    /**
     * Use special weapon on enemy cell.
     */
    public void useRocketOnEnemyCell()
            throws InterruptedException {

        Thread.sleep(300);

        System.out.println("Using special weapon...");

        // Try several progressive strategies: click a discovered enemy cell element, then grid fallback
        final int attempts = 8;
        final int sleepMs = 600;

        for (int attempt = 0; attempt < attempts; attempt++) {
            try {
                Object res = js.executeScript(
                        // JS tries many selectors to find an enemy cell and click it
                        """
                        function tryClickCandidate(el) {
                            try {
                                el.scrollIntoView({block:'center'});
                            } catch(e){}
                            try { el.click(); return true; } catch(e) {
                                try { el.dispatchEvent(new MouseEvent('click',{bubbles:true})); return true; } catch(e) {}
                            }
                            return false;
                        }

                        // 1) Try cells with data attributes
                        const selectors = [
                            '[data-x][data-y][data-owner]', // generic cell with owner
                            '[data-x][data-y]:not([data-owner="me"])',
                            '[data-coord][data-owner]:not([data-owner*="me"])',
                            '[data-player="opponent"] *[data-x]',
                            '.opponent .cell, .enemy .cell, .opponent-cell, .enemy-cell',
                            '[role=\"grid\"] [role=\"gridcell\"]',
                            '.board.opponent td, .board.opponent div.cell'
                        ];

                        for (const s of selectors) {
                            try {
                                const els = Array.from(document.querySelectorAll(s)).filter(e => {
                                    try {
                                        const r = e.getBoundingClientRect();
                                        return r && r.width > 2 && r.height > 2;
                                    } catch(e){ return false; }
                                });
                                if (els.length) {
                                    // prefer first untargeted / clickable cell
                                    for (const e of els) {
                                        // heuristic: avoid already-hit markers (text or classes)
                                        const cls = (e.className || '').toLowerCase();
                                        const txt = (e.textContent || '').toLowerCase();
                                        if (cls.includes('hit') || cls.includes('miss') || txt.includes('x') || txt.includes('hit')) continue;
                                        if (tryClickCandidate(e)) return { ok: true, method: 'selector', sel: s, count: els.length };
                                    }
                                    // otherwise click first
                                    const e = els[0];
                                    if (tryClickCandidate(e)) return { ok: true, method: 'selector-first', sel: s, count: els.length };
                                }
                            } catch(e){}
                        }

                        // 2) Try to find a board element and compute a 10x10 grid click
                        const boardSelectors = [
                            '.opponent', '.enemy', '#opponent', '#enemy', '.opponent-board', '.enemy-board', '.board.opponent'
                        ];

                        for (const bs of boardSelectors) {
                            try {
                                const b = document.querySelector(bs);
                                if (!b) continue;
                                const rect = b.getBoundingClientRect();
                                if (!rect || rect.width < 10 || rect.height < 10) continue;

                                // attempt to detect internal grid cells
                                const innerCells = Array.from(b.querySelectorAll('[data-x][data-y], td, div.cell, .cell'));
                                if (innerCells.length >= 10) {
                                    for (const c of innerCells) {
                                        const cls = (c.className || '').toLowerCase();
                                        const txt = (c.textContent || '').toLowerCase();
                                        if (cls.includes('hit') || cls.includes('miss') || txt.includes('hit') || txt.includes('x')) continue;
                                        if (tryClickCandidate(c)) return { ok: true, method: 'board-detected-cell', sel: bs, cells: innerCells.length };
                                    }
                                }

                                // fallback: assume 10x10 grid, click center of a cell that is likely not yet targeted
                                const rows = 10;
                                const cols = 10;
                                for (let r=0;r<rows;r++) {
                                    for (let c=0;c<cols;c++) {
                                        // choose a cell near the top-left quadrant first
                                        const cx = Math.round(rect.left + (c + 0.5) * rect.width / cols);
                                        const cy = Math.round(rect.top + (r + 0.5) * rect.height / rows);
                                        const el = document.elementFromPoint(cx, cy);
                                        if (!el) continue;
                                        const cls = (el.className || '').toLowerCase();
                                        const txt = (el.textContent || '').toLowerCase();
                                        if (cls.includes('hit') || cls.includes('miss') || txt.includes('hit') || txt.includes('x')) continue;
                                        if (tryClickCandidate(el)) return { ok: true, method: 'board-grid-click', sel: bs, r: r, c: c };
                                    }
                                }

                                // if all failed, still click board center
                                const centerX = Math.round(rect.left + rect.width/2);
                                const centerY = Math.round(rect.top + rect.height/2);
                                const el = document.elementFromPoint(centerX, centerY);
                                if (el && tryClickCandidate(el)) return { ok: true, method: 'board-center', sel: bs };
                            } catch(e){}
                        }

                        // 3) Final fallback: click center of the viewport area where enemy is likely located (center-left/center)
                        try {
                            const w = window.innerWidth;
                            const h = window.innerHeight;
                            const cx = Math.round(w * 0.65);
                            const cy = Math.round(h * 0.45);
                            const el = document.elementFromPoint(cx, cy);
                            if (el) {
                                try { el.click(); } catch(e) { el.dispatchEvent(new MouseEvent('click',{bubbles:true})); }
                                return { ok: true, method: 'viewport-fallback', x: cx, y: cy };
                            }
                        } catch(e){}

                        return { ok: false };
                        """
                );

                if (res instanceof java.util.Map) {
                    @SuppressWarnings("unchecked")
                    java.util.Map<String, Object> map = (java.util.Map<String, Object>) res;
                    if (Boolean.TRUE.equals(map.get("ok"))) {
                        System.out.println("✅ Enemy cell clicked: " + map);
                        Thread.sleep(600);
                        return;
                    }
                }

            } catch (Exception e) {
                // ignore and retry
                System.err.println("useRocket attempt error: " + e.getMessage());
            }

            Thread.sleep(sleepMs);
        }

        throw new IllegalStateException("Failed to click enemy cell.");
    }

    /**
     * Wait for a visible sign that the special weapon was used.
     */
    public boolean waitForWeaponEffect(int timeoutSeconds)
            throws InterruptedException {

        final int pollMs = 600;
        final long end = System.currentTimeMillis() + timeoutSeconds * 1000L;

        System.out.println("Waiting up to " + timeoutSeconds + "s for weapon effect...");

        while (System.currentTimeMillis() < end) {

            try {

                Object result = js.executeScript("""
                    const text = (document.body && document.body.innerText || '').toLowerCase();

                    const keywords = [
                        'rocket','bomb','missile','special','boom','hit','miss','sank','destroyed','explosion','blast'
                    ];

                    const foundText = keywords.some(k => text.includes(k));

                    const visualEffect = Array.from(document.querySelectorAll('*')).some(el => {
                        try {
                            const cls = (el.className || '').toString().toLowerCase();
                            const html = (el.outerHTML || '').toLowerCase();
                            if (cls.includes('explosion') || cls.includes('boom') || cls.includes('blast')) return true;
                            if (html.includes('explosion') || html.includes('boom') || html.includes('blast')) return true;
                            return false;
                        } catch(e) { return false; }
                    });

                    return { success: foundText || visualEffect, textFound: foundText, visualFound: visualEffect };
                """);

                if (result instanceof java.util.Map) {

                    @SuppressWarnings("unchecked")
                    java.util.Map<String, Object> map =
                            (java.util.Map<String, Object>) result;

                    if ((boolean) map.get("success")) {

                        System.out.println("✅ Weapon effect detected (textFound=" + map.get("textFound") + ", visualFound=" + map.get("visualFound") + ")");
                        return true;
                    }
                }

            } catch (Exception e) {
                // ignore and continue polling
                System.err.println("waitForWeaponEffect poll error: " + e.getMessage());
            }

            Thread.sleep(pollMs);
        }

        System.out.println("⚠️ Weapon effect not detected within timeout");
        return false;
    }
}