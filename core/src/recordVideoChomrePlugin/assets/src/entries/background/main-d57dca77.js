import { o as d } from "../../../constants-1491974f.js";
import { u as f, h as l, g, d as m } from "../../../utils-e2656ec5.js";
import { i as h } from "../../../sdk-3856d75a.js";

(function () {
    try {
        var e = typeof window < "u" ? window : typeof global < "u" ? global : typeof self < "u" ? self : {},
            r = new Error().stack;
        r && (e._sentryDebugIds = e._sentryDebugIds || {}, e._sentryDebugIds[r] = "180fe48e-5574-4686-9b3a-51c422d838c1", e._sentryDebugIdIdentifier = "sentry-dbid-180fe48e-5574-4686-9b3a-51c422d838c1")
    } catch {}
})();

h({
    dsn: "https://d12dd277a192c6ca69ba59ebb958e6e2@o82598.ingest.sentry.io/4505787043479552"
});

let i = !1,
    s, a = null,
    n = null;
const o = new Set;

function u() {
    chrome.runtime.sendMessage({
        type: "stop-recording",
        target: "offscreen"
    }), a && chrome.tabs.sendMessage(a, {
        type: "stop-recording"
    }), s === "desktop" && n && chrome.tabs.sendMessage(n, {
        type: "stop-recording"
    })
}

chrome.tabs.onUpdated.addListener((e, r, t) => {
    r.status === "loading" && o.has(e) && chrome.scripting.executeScript({
        target: {
            tabId: e
        },
        files: ["/src/entries/contentScript/primary/main.js"]
    })
});

chrome.tabs.onRemoved.addListener(e => {
    o.has(e) && o.delete(e)
});

chrome.action.onClicked.addListener(async e => {
    e.id && (i ? u() : o.has(e.id) ? chrome.tabs.sendMessage(e.id, {
        type: "show-controlbar"
    }) : (await chrome.scripting.executeScript({
        target: {
            tabId: e.id
        },
        files: ["/src/entries/contentScript/primary/main.js"]
    }), chrome.tabs.sendMessage(e.id, {
        type: "show-controlbar"
    }), o.add(e.id)))
});

async function p(e) {
    await l() || await chrome.offscreen.createDocument({
        url: d,
        justification: "Recording from chrome.tabCapture API",
        reasons: [chrome.offscreen.Reason.USER_MEDIA]
    }), s = e.recordingMode ?? null;
    const t = await g();
    if (t.id) {
        if (s && ["tab", "area"].includes(s)) {
            const c = await m(t.id);
            chrome.runtime.sendMessage({
                type: "start-recording",
                target: "offscreen",
                data: {
                    streamId: c,
                    width: t.width,
                    height: t.height,
                    ...e
                }
            }), a = t.id
        } else n = (await chrome.tabs.create({
            url: `/src/entries/tabs/main.html?tabId=${t.id}`
        })).id ?? null;
        chrome.action.setBadgeText({
            text: "REC"
        }), chrome.action.setBadgeTextColor({
            color: "#ffffff"
        }), chrome.action.setBadgeBackgroundColor({
            color: "#dc2626"
        }), i = !0
    }
}

chrome.runtime.onMessage.addListener(async (e, r, t) => {
    if (e.target === "background") switch (e.type) {
        case "recording-complete":
            s === "desktop" ? n && chrome.tabs.update(n, {
                active: !0
            }) : chrome.tabs.create({
                url: `/src/entries/tabs/main.html?videoUrl=${encodeURIComponent(e.videoUrl)}`
            }), chrome.action.setBadgeText({
                text: ""
            }), f.setState({
                isRecording: !1
            }), i = !1, a = null, s = null;
            break;
        case "start-recording":
            p(e.data);
            break;
        default:
            throw new Error("Unrecognized message:", e.type)
    }
});



