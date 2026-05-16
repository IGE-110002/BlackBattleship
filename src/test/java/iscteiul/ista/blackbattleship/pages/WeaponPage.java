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
        final int tries = 8;
        final int sleepMs = 500;

        for (int attempt = 0; attempt < tries; attempt++) {
            try {
                StringBuilder sb = new StringBuilder();

                sb.append("function doClick(el){");
                sb.append(" if(!el) return false;");
                sb.append(" try{ el.focus && el.focus(); }catch(e){}");
                sb.append(" try{ el.scrollIntoView && el.scrollIntoView({block:'center'}); }catch(e){}");
                sb.append(" try{ el.click && el.click(); return true; }catch(e){}");
                sb.append(" try{ el.dispatchEvent && el.dispatchEvent(new MouseEvent('click',{bubbles:true})); return true; }catch(e){}");
                sb.append(" return false;");
                sb.append("}\n");

                sb.append("const textNeedle='attack your opponent';\n");
                sb.append("const nodes=Array.from(document.querySelectorAll('h1,h2,h3,div,span'))");
                sb.append(".filter(n=>(n.textContent||'').toLowerCase().includes(textNeedle));\n");

                sb.append("if(nodes.length){");
                sb.append(" let h=nodes[0];");
                sb.append(" let sib=h.nextElementSibling;");
                sb.append(" let depth=0;");
                sb.append(" while(sib && depth<10){");
                sb.append("  try{");
                sb.append("   const weapons=Array.from(sib.querySelectorAll('button, img, [role=\"button\"], div'))");
                sb.append("   .filter(e=>{");
                sb.append("     try{");
                sb.append("       const r=e.getBoundingClientRect();");
                sb.append("       const txt=(e.textContent||'').toLowerCase();");
                sb.append("       return r && r.width>25 && r.height>25");
                sb.append("       && !txt.includes('abort') && !txt.includes('resign')");
                sb.append("       && !txt.includes('quit') && !txt.includes('leave')");
                sb.append("       && !txt.includes('exit');");
                sb.append("     }catch(e){return false;}");
                sb.append("   });");

                sb.append("   if(weapons.length){");
                sb.append("     const index=Math.floor(Math.random()*weapons.length);");
                sb.append("     const weapon=weapons[index];");
                sb.append("     weapon.style.outline='5px solid red';");
                sb.append("     weapon.style.boxShadow='0 0 15px red';");
                sb.append("     const txt=(weapon.textContent||weapon.alt||weapon.title||'weapon-'+index).trim();");
                sb.append("     if(doClick(weapon)){");
                sb.append("       return {ok:true, method:'random-header-sibling', selectedIndex:index, total:weapons.length, weaponText:txt};");
                sb.append("     }");
                sb.append("   }");
                sb.append("  }catch(e){}");
                sb.append("  sib=sib.nextElementSibling;");
                sb.append("  depth++;");
                sb.append(" }");
                sb.append("}\n");

                sb.append("return {ok:false, reason:'random-weapon-not-found'};");

                Object out = js.executeScript(sb.toString());

                if (out instanceof Map && Boolean.TRUE.equals(((Map<String, Object>) out).get("ok"))) {
                    System.out.println("✅ Random weapon selected: " + out);
                    Thread.sleep(700);
                    return;
                } else {
                    System.out.println("selectAndActivateWeapon random attempt result=" + out);
                }

            } catch (Exception e) {
                System.err.println("selectAndActivateWeapon random attempt error: " + e.getMessage());
            }

            Thread.sleep(sleepMs);
        }

        captureDiagnostics("random-weapon-select-fail");
        throw new IllegalStateException("Failed to select random weapon.");
    }
    @SuppressWarnings("unchecked")
    public void useWeaponOnEnemyCell() throws InterruptedException {
        final int tries = 10;
        final int sleepMs = 600;

        for (int i = 0; i < tries; i++) {
            try {
                StringBuilder sb = new StringBuilder();

                sb.append("function doClick(el){");
                sb.append(" if(!el) return false;");
                sb.append(" try{ el.focus && el.focus(); }catch(e){}");
                sb.append(" try{ el.click && el.click(); return true;}catch(e){}");
                sb.append(" try{ el.dispatchEvent && el.dispatchEvent(new MouseEvent('click',{bubbles:true})); return true;}catch(e){}");
                sb.append(" return false;");
                sb.append("}\n");

                sb.append("const selectors=['.opponent .cell','.enemy .cell','.opponent-cell','.enemy-cell','.opponent td','.enemy td','[role=\"grid\"] [role=\"gridcell\"]'];\n");
                sb.append("let cells=[];\n");
                sb.append("for(const s of selectors){");
                sb.append(" try{");
                sb.append("   cells=Array.from(document.querySelectorAll(s)).filter(e=>{");
                sb.append("     const r=e.getBoundingClientRect();");
                sb.append("     const cls=(e.className||'').toLowerCase();");
                sb.append("     const txt=(e.textContent||'').toLowerCase();");
                sb.append("     return r && r.width>3 && r.height>3 && !cls.includes('hit') && !cls.includes('miss') && !txt.includes('x');");
                sb.append("   });");
                sb.append("   if(cells.length) break;");
                sb.append(" }catch(e){}");
                sb.append("}\n");

                sb.append("if(cells.length){");
                sb.append(" const index=Math.floor(Math.random()*cells.length);");
                sb.append(" const target=cells[index];");
                sb.append(" target.style.outline='5px solid blue';");
                sb.append(" target.style.boxShadow='0 0 18px blue';");
                sb.append(" target.style.borderRadius='50%';");
                sb.append(" if(doClick(target)){");
                sb.append("   return {ok:true, method:'random-opponent-cell', selectedCell:index, totalCells:cells.length};");
                sb.append(" }");
                sb.append("}\n");

                sb.append("return {ok:false, reason:'no-random-opponent-cell-found'};");

                Object out = js.executeScript(sb.toString());

                if (out instanceof Map && Boolean.TRUE.equals(((Map<String, Object>) out).get("ok"))) {
                    System.out.println("✅ Random opponent target selected and clicked: " + out);
                    Thread.sleep(800);
                    return;
                } else {
                    System.out.println("useWeaponOnEnemyCell random attempt: " + out);
                }

            } catch (Exception e) {
                System.err.println("useWeaponOnEnemyCell random attempt error: " + e.getMessage());
            }

            Thread.sleep(sleepMs);
        }

        captureDiagnostics("random-weapon-use-fail");
        throw new IllegalStateException("Failed to use weapon on random opponent cell.");
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

}