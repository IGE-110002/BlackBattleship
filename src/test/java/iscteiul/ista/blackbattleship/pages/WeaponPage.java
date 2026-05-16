package iscteiul.ista.blackbattleship.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.OutputType;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * WeaponPage: robust selection/use of special weapons and chat helper methods.
 *
 * This version constructs JS scripts using StringBuilder to avoid fragile
 * long literal concatenations and problematic escape sequences.
 */
public class WeaponPage {

    private final WebDriver driver;
    private final JavascriptExecutor js;

    public WeaponPage(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
    }

    public void captureDiagnostics(String prefix) {
        try {
            String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String out = "target/debug-screenshots";
            Files.createDirectories(Paths.get(out));
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String shot = out + File.separator + prefix + "-" + ts + ".png";
            Files.copy(screenshot.toPath(), Paths.get(shot), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("✅ Saved diagnostics: " + shot);
        } catch (Exception e) {
            System.err.println("Failed to save diagnostics: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> scanAndClickArea(int x1, int y1, int x2, int y2, int step) {
        StringBuilder sb = new StringBuilder();
        sb.append("const x1 = arguments[0], y1 = arguments[1], x2 = arguments[2], y2 = arguments[3], step = arguments[4];\n");
        sb.append("function doClick(el){ if(!el) return false; try{ el.focus && el.focus(); el.scrollIntoView && el.scrollIntoView({block:'center'}); }catch(e){} try{ el.click && el.click(); return true;}catch(e){} try{ el.dispatchEvent && el.dispatchEvent(new MouseEvent('click',{bubbles:true})); return true;}catch(e){} return false; }\n");
        sb.append("function isClickable(el){ if(!el) return false; try{ const tag=(el.tagName||'').toUpperCase(); if(tag==='BUTTON'||tag==='A') return true; if(el.getAttribute && (el.getAttribute('role')||'').toLowerCase().includes('button')) return true; if(el.hasAttribute && el.hasAttribute('onclick')) return true; const s=window.getComputedStyle(el); if(s && s.pointerEvents==='none') return false; if(s && s.cursor && s.cursor.includes('pointer')) return true; return false; }catch(e){return false;} }\n");
        sb.append("for(let x=x1;x<=x2;x+=step){ for(let y=y1;y<=y2;y+=step){ try{ const el=document.elementFromPoint(x,y); if(!el) continue; if(isClickable(el)){ if(doClick(el)) return {ok:true, method:'scan', tag:el.tagName, x:x, y:y, text:(el.textContent||'').substring(0,80)}; } const p = el.closest && el.closest('button, [role=\"button\"], a'); if(p){ if(doClick(p)) return {ok:true, method:'scan-parent', tag:p.tagName, x:x, y:y, text:(p.textContent||'').substring(0,80)}; } }catch(e){} } }\n");
        sb.append("return { ok:false, reason:'no-clickable-found' };\n");

        Object res = js.executeScript(sb.toString(), x1, y1, x2, y2, step);
        if (res instanceof Map) return (Map<String, Object>) res;
        return Map.of("ok", false);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> bruteForceClicks(int x1, int y1, int x2, int y2, int attempts) {
        StringBuilder sb = new StringBuilder();
        sb.append("const x1=arguments[0], y1=arguments[1], x2=arguments[2], y2=arguments[3], attempts=arguments[4];\n");
        sb.append("function doClick(el){ if(!el) return false; try{ el.focus && el.focus(); el.scrollIntoView && el.scrollIntoView({block:'center'}); }catch(e){} try{ el.click && el.click(); return true;}catch(e){} try{ el.dispatchEvent && el.dispatchEvent(new MouseEvent('click',{bubbles:true})); return true;}catch(e){} return false; }\n");
        sb.append("for(let i=0;i<attempts;i++){ const rx = Math.floor(x1 + Math.random()*(x2-x1)); const ry = Math.floor(y1 + Math.random()*(y2-y1)); try{ const el = document.elementFromPoint(rx,ry); if(el && doClick(el)) return { ok:true, x:rx, y:ry, attempt:i }; const p = el && el.closest && el.closest('button, [role=\"button\"], a'); if(p && doClick(p)) return { ok:true, x:rx, y:ry, attempt:i, parent:true }; }catch(e){} }\n");
        sb.append("return { ok:false, reason:'random-clicks-failed' };\n");

        Object out = js.executeScript(sb.toString(), x1, y1, x2, y2, attempts);
        if (out instanceof Map) return (Map<String, Object>) out;
        return Map.of("ok", false);
    }

    @SuppressWarnings("unchecked")
    public void selectAndActivateWeapon() throws InterruptedException {

        Thread.sleep(1200);

        StringBuilder sb = new StringBuilder();

        sb.append("function clickReal(x,y){");
        sb.append(" x=Math.max(1, Math.min(window.innerWidth-2, x));");
        sb.append(" y=Math.max(1, Math.min(window.innerHeight-2, y));");
        sb.append(" const el=document.elementFromPoint(x,y);");
        sb.append(" if(!el) return {ok:false, reason:'no-element', x:x, y:y, vw:window.innerWidth, vh:window.innerHeight};");
        sb.append(" const target=(el.closest && el.closest('button,[role=\"button\"],div,svg,canvas')) || el;");
        sb.append(" try{target.style.outline='5px solid red'; target.style.boxShadow='0 0 20px red';}catch(e){}");
        sb.append(" const opts={bubbles:true,cancelable:true,clientX:x,clientY:y};");
        sb.append(" try{target.dispatchEvent(new MouseEvent('mousedown',opts));}catch(e){}");
        sb.append(" try{target.dispatchEvent(new MouseEvent('mouseup',opts));}catch(e){}");
        sb.append(" try{target.dispatchEvent(new MouseEvent('click',opts));}catch(e){}");
        sb.append(" try{target.click();}catch(e){}");
        sb.append(" return {ok:true,x:x,y:y,tag:target.tagName};");
        sb.append("}");

        sb.append("function findEnemyBoard(){");
        sb.append(" const all=Array.from(document.querySelectorAll('div,canvas,svg'));");
        sb.append(" const boards=all.map(e=>({e:e,r:e.getBoundingClientRect()}))");
        sb.append(" .filter(o=>");
        sb.append("   o.r.width>=250 && o.r.width<=650 &&");
        sb.append("   o.r.height>=250 && o.r.height<=650 &&");
        sb.append("   o.r.left>window.innerWidth*0.35 &&");
        sb.append("   o.r.right<window.innerWidth-20 &&");
        sb.append("   o.r.top>100 &&");
        sb.append("   o.r.bottom<window.innerHeight-80");
        sb.append(" )");
        sb.append(" .sort((a,b)=>a.r.left-b.r.left);");
        sb.append(" return boards.length ? boards[0].r : null;");
        sb.append("}");

        sb.append("const r=findEnemyBoard();");
        sb.append("if(!r) return {ok:false, reason:'enemy-board-not-found', vw:window.innerWidth, vh:window.innerHeight};");

        // grey missile icon = second weapon under enemy board
        sb.append("const x=Math.round(r.left + r.width * 0.31);");
        sb.append("const y=Math.round(r.bottom + 38);");

        sb.append("return clickReal(x,y);");

        Object out = js.executeScript(sb.toString());

        if (out instanceof Map &&
                Boolean.TRUE.equals(((Map<String, Object>) out).get("ok"))) {
            System.out.println("🚀 Grey missile selected: " + out);
            Thread.sleep(1200);
            return;
        }

        captureDiagnostics("grey-missile-select-fail");
        throw new IllegalStateException("Failed to select grey missile: " + out);
    }
    @SuppressWarnings("unchecked")
    public void useWeaponOnEnemyCell() throws InterruptedException {

        Thread.sleep(1000);

        StringBuilder sb = new StringBuilder();

        sb.append("function realClick(x,y){");
        sb.append(" const el=document.elementFromPoint(x,y);");
        sb.append(" if(!el) return {ok:false, reason:'no-element', x:x, y:y};");
        sb.append(" const target=(el.closest && el.closest('div,td,button,canvas')) || el;");
        sb.append(" try{target.style.outline='5px solid blue';}catch(e){}");
        sb.append(" const opts={bubbles:true,cancelable:true,clientX:x,clientY:y};");
        sb.append(" try{target.dispatchEvent(new MouseEvent('mousedown',opts));}catch(e){}");
        sb.append(" try{target.dispatchEvent(new MouseEvent('mouseup',opts));}catch(e){}");
        sb.append(" try{target.dispatchEvent(new MouseEvent('click',opts));}catch(e){}");
        sb.append(" try{target.click();}catch(e){}");
        sb.append(" return {ok:true, x:x, y:y};");
        sb.append("}");

        sb.append("const vw=window.innerWidth;");
        sb.append("const vh=window.innerHeight;");

        // SAFE CENTER AREA OF ENEMY BOARD
        sb.append("const x=Math.round(vw*0.685);");
        sb.append("const y=Math.round(vh*0.490);");

        sb.append("return realClick(x,y);");

        Object out = js.executeScript(sb.toString());

        if (out instanceof Map &&
                Boolean.TRUE.equals(((Map<String, Object>) out).get("ok"))) {

            System.out.println("✅ Missile launched on enemy board: " + out);
                    Thread.sleep(1000);
            return;
        }

        captureDiagnostics("enemy-board-click-fail");
        throw new IllegalStateException("Failed to use missile on enemy board.");
    }
    @SuppressWarnings("unchecked")
    public boolean waitForWeaponEffect(int timeoutSeconds) throws InterruptedException {
        long end = System.currentTimeMillis() + timeoutSeconds * 1000L;
        final int poll = 700;
        while (System.currentTimeMillis() < end) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("const t = (document.body && document.body.innerText || '').toLowerCase();\n");
                sb.append("const keywords = ['rocket','bomb','missile','special','boom','hit','miss','sank','destroyed','explosion','blast'];\n");
                sb.append("const found = keywords.some(k=> t.includes(k));\n");
                sb.append("const visual = Array.from(document.querySelectorAll('*')).some(el=>{ try{ const cl=(el.className||'').toLowerCase(); const html=(el.outerHTML||'').toLowerCase(); if(cl.includes('explosion')||html.includes('explosion')||cl.includes('boom')||html.includes('boom')) return true; return false;}catch(e){return false;} });\n");
                sb.append("return { ok: found || visual, text: found, visual: visual };\n");

                Object out = js.executeScript(sb.toString());
                if (out instanceof Map && Boolean.TRUE.equals(((Map<String, Object>) out).get("ok"))) {
                    System.out.println("✅ Weapon effect detected: " + out);
                    return true;
                }
            } catch (Exception e) {
                System.err.println("waitForWeaponEffect poll error: " + e.getMessage());
            }
            Thread.sleep(poll);
        }
        System.out.println("⚠️ waitForWeaponEffect: timeout");
        return false;
    }

    @SuppressWarnings("unchecked")
    public void sendChatMessage(String message) throws InterruptedException {
        final int tries = 6;
        final int sleepMs = 400;

        boolean injected = false;
        for (int i = 0; i < tries && !injected; i++) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("const msg = arguments[0];\n");
                sb.append("function dispatchInput(el, v){ try{ el.focus && el.focus(); }catch(e){} try{ if(el.tagName && el.tagName.toLowerCase()==='textarea' || el.tagName && el.tagName.toLowerCase()==='input'){ el.value = v; } else { el.textContent = v; el.innerText = v; } }catch(e){} try{ el.setAttribute && el.setAttribute('value', v); }catch(e){}\n");
                sb.append(" try{ el.dispatchEvent && el.dispatchEvent(new Event('input', {bubbles:true})); }catch(e){} try{ el.dispatchEvent && el.dispatchEvent(new Event('change', {bubbles:true})); }catch(e){} }\n");
                sb.append("const panelSelectors = ['[data-testid*=\"chat\"]','[data-test*=\"chat\"]','[data-panel*=\"chat\"]','.chat','.chat-panel','.chat-area','.chat-box'];\n");
                sb.append("let panel=null; for(const s of panelSelectors){ try{ const p=document.querySelector(s); if(p){ panel=p; break; } }catch(e){} }\n");
                sb.append("if(!panel){ const cands=Array.from(document.querySelectorAll('div,section,aside')); for(const c of cands){ try{ const text=(c.innerText||'').toLowerCase(); if(text.includes('chat')||text.includes('messages')){ panel=c; break; } }catch(e){} } }\n");
                sb.append("const root = panel || document;\n");
                sb.append("const inputSelectors = ['textarea', 'input[type=\"text\"]', '[contenteditable=\"true\"]'];\n");
                sb.append("let input=null; for(const s of inputSelectors){ try{ const el=root.querySelector(s); if(el){ input=el; break; } }catch(e){} }\n");
                sb.append("if(!input){ return { ok:false, reason:'no-input', panelFound: !!panel } }\n");
                sb.append("try{ if(input.getAttribute && input.getAttribute('contenteditable')==='true'){ input.focus && input.focus(); input.innerText = msg; input.textContent = msg; try{ input.dispatchEvent && input.dispatchEvent(new Event('input',{bubbles:true})); }catch(e){} } else { dispatchInput(input, msg); } }catch(e){}\n");
                sb.append("return { ok:true, method:'injected', panelPresent: !!panel, inputTag: input.tagName || '', isContentEditable: input.getAttribute && input.getAttribute('contenteditable')==='true' };\n");

                Object out = js.executeScript(sb.toString(), message);
                if (out instanceof Map && Boolean.TRUE.equals(((Map<String, Object>) out).get("ok"))) {
                    injected = true;
                    System.out.println("✅ Message injected into input (method=injected) info=" + out);
                    Thread.sleep(450); // give UI a little time to enable send
                    break;
                } else {
                    System.out.println("sendChatMessage: inject attempt result=" + out);
                }
            } catch (Exception e) {
                System.err.println("sendChatMessage: inject attempt error: " + e.getMessage());
            }
            Thread.sleep(sleepMs);
        }

        if (!injected) {
            captureDiagnostics("chat-send-no-input");
            throw new IllegalStateException("Failed to locate chat input to send message (see target/debug-screenshots).");
        }
// Replace only the clickedSend section with this
        boolean clickedSend = false;
        for (int i = 0; i < 6 && !clickedSend; i++) {
            try {
                StringBuilder sb = new StringBuilder();

                sb.append("const input = document.querySelector('textarea, input[type=\"text\"], [contenteditable=\"true\"]');\n");
                sb.append("if(!input) return {ok:false, reason:'no-input'};\n");
                sb.append("const r = input.getBoundingClientRect();\n");
                sb.append("const x = Math.round(r.right + 35);\n");
                sb.append("const y = Math.round(r.top + r.height / 2);\n");
                sb.append("const el = document.elementFromPoint(x, y);\n");
                sb.append("if(!el) return {ok:false, reason:'no-send-at-point', x:x, y:y};\n");
                sb.append("const btn = (el.closest && el.closest('button, [role=\"button\"], a, div')) || el;\n");
                sb.append("try{ btn.style.outline='5px solid green'; }catch(e){}\n");
                sb.append("try{ btn.click(); }catch(e){ try{ btn.dispatchEvent(new MouseEvent('click',{bubbles:true,clientX:x,clientY:y})); }catch(e2){} }\n");
                sb.append("return {ok:true, method:'send-button-near-input', x:x, y:y, tag:btn.tagName, text:(btn.innerText||btn.textContent||'').substring(0,80)};\n");

                Object out = js.executeScript(sb.toString());

                if (out instanceof Map && Boolean.TRUE.equals(((Map<String, Object>) out).get("ok"))) {
                    System.out.println("✅ Clicked chat send button near input (info=" + out + ")");
                    clickedSend = true;
                    Thread.sleep(600);
                    break;
                } else {
                    System.out.println("sendChatMessage send-button attempt result=" + out);
                }
            } catch (Exception e) {
                System.err.println("sendChatMessage: send-button attempt error: " + e.getMessage());
            }

            Thread.sleep(350);
        }

        if (!clickedSend) {
            // Try pressing Enter via JS
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("const panelSelectors=['[data-testid*=\"chat\"]','[data-test*=\"chat\"]','[data-panel*=\"chat\"]','.chat','.chat-panel','.chat-area','.chat-box']; let panel=null; for(const s of panelSelectors){ try{ const p=document.querySelector(s); if(p){ panel=p; break; } }catch(e){} }\n");
                sb.append("const root=panel||document; let input = root.querySelector('textarea') || root.querySelector('input[type=\"text\"]') || root.querySelector('[contenteditable=\"true\"]') || document.querySelector('textarea') || document.querySelector('input[type=\"text\"]') || document.querySelector('[contenteditable=\"true\"]'); if(!input) return { ok:false, reason:'no-input-for-enter' } try{ input.focus && input.focus(); }catch(e){} try{ const ev=new KeyboardEvent('keydown',{key:'Enter',code:'Enter',bubbles:true}); input.dispatchEvent && input.dispatchEvent(ev); }catch(e){} return { ok:true, method:'enter-pressed-js' };\n");

                Object out = js.executeScript(sb.toString());
                if (out instanceof Map && Boolean.TRUE.equals(((Map<String, Object>) out).get("ok"))) {
                    System.out.println("✅ Sent Enter on chat input as fallback (JS) (" + out + ")");
                    Thread.sleep(450);
                    clickedSend = true;
                } else {
                    System.out.println("Could not press Enter via JS fallback: " + out);
                }
            } catch (Exception e) {
                System.err.println("sendChatMessage: Enter fallback JS error: " + e.getMessage());
            }
        }

        if (!clickedSend) {
            // Selenium fallback: sendKeys ENTER
            try {
                List<WebElement> inputs = driver.findElements(By.cssSelector("textarea, input[type='text'], [contenteditable='true']"));
                if (!inputs.isEmpty()) {
                    WebElement el = inputs.get(0);
                    try {
                        el.sendKeys(Keys.ENTER);
                        System.out.println("✅ Sent Enter on input via Selenium Keys fallback");
                        Thread.sleep(400);
                        clickedSend = true;
                    } catch (Exception e) {
                        System.err.println("Selenium sendKeys ENTER failed: " + e.getMessage());
                    }
                } else {
                    System.out.println("Selenium fallback: no inputs found to send ENTER");
                }
            } catch (Exception e) {
                System.err.println("sendChatMessage: Selenium fallback error: " + e.getMessage());
            }
        }

        boolean visible = waitForChatMessage(message, 10);
        if (!visible) {
            captureDiagnostics("chat-no-confirm");
            throw new IllegalStateException("Chat message not visible after sending (see target/debug-screenshots).");
        }
    }

    @SuppressWarnings("unchecked")
    public boolean waitForChatMessage(String message, int timeoutSeconds) throws InterruptedException {
        long end = System.currentTimeMillis() + timeoutSeconds * 1000L;
        final int poll = 700;
        String lower = message.toLowerCase().trim();

        while (System.currentTimeMillis() < end) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("const needle = arguments[0];\n");
                sb.append("const panelSelectors=['[data-testid*=\"chat\"]','[data-test*=\"chat\"]','[data-panel*=\"chat\"]','.chat','.chat-panel','.chat-area','.chat-box'];\n");
                sb.append("for(const s of panelSelectors){ try{ const p=document.querySelector(s); if(!p) continue; const messages = Array.from(p.querySelectorAll('*')).filter(el=>{ try{ const t=(el.textContent||'').toLowerCase(); return t && t.length>0;}catch(e){return false;} }).slice(-40); for(const m of messages){ try{ const t=(m.textContent||'').toLowerCase(); if(t.includes(needle)) return { ok:true, method:'panel', selector:s, snippet:t }; }catch(e){} } }catch(e){} }\n");
                sb.append("try{ const body=(document.body && document.body.innerText||'').toLowerCase(); if(body.includes(needle)) return { ok:true, method:'body' }; }catch(e){}\n");
                sb.append("return { ok:false };\n");

                Object found = js.executeScript(sb.toString(), lower);
                if (found instanceof Map && Boolean.TRUE.equals(((Map<String, Object>) found).get("ok"))) {
                    System.out.println("✅ Chat message confirmed (info=" + found + ")");
                    return true;
                } else {
                    if (found instanceof Map) {
                        System.out.println("waitForChatMessage: not found yet");
                    }
                }
            } catch (Exception e) {
                System.err.println("waitForChatMessage poll error: " + e.getMessage());
            }
            Thread.sleep(poll);
        }
        System.out.println("⚠️ waitForChatMessage: timeout");
        return false;
    }
    public void sendRandomTaunt() throws InterruptedException {
        String[] taunts = {
                "Boom 💥",
                "Direct hit 🚀",
                "Weapon attack successful ✅",
                "Nice shot 😄",
                "Target locked 🎯"
        };

        int index = new java.util.Random().nextInt(taunts.length);
        String msg = taunts[index];

        System.out.println("💬 Random taunt selected: " + msg);
        sendChatMessage(msg);
    }
    @SuppressWarnings("unchecked")
    public int countSuccessfulHits() {
        StringBuilder sb = new StringBuilder();

        sb.append("const hitWords = ['hit','sank','destroyed','boom','explosion','blast'];");
        sb.append("const body = (document.body && document.body.innerText || '').toLowerCase();");
        sb.append("let count = 0;");
        sb.append("for(const word of hitWords){");
        sb.append(" if(body.includes(word)) count++;");
        sb.append("}");
        sb.append("return count;");

        Object result = js.executeScript(sb.toString());

        int hits = 0;
        if (result instanceof Number) {
            hits = ((Number) result).intValue();
        }

        System.out.println("📊 Successful hit indicators found: " + hits);
        return hits;
    }
    public void useWeaponAndSendRandomTaunt() throws InterruptedException {
        selectAndActivateWeapon();

        useWeaponOnEnemyCell();

        boolean effect = waitForWeaponEffect(10);
        if (!effect) {
            captureDiagnostics("extra-no-weapon-effect");
            throw new IllegalStateException("Weapon effect was not detected.");
        }

        int hits = countSuccessfulHits();
        System.out.println("🎯 Hit count after weapon attack: " + hits);

        sendRandomTaunt();

        System.out.println("✅ Extra flow completed: weapon used + hit counted + random taunt sent.");
    }
}