function createDivWithId(id) {
    const div = document.createElement("div");
    div.id = id; // Set the id attribute
    return div;
}

import {
    r as on,
    j as M,
    $ as _l,
    i as kl,
    c as Ll
} from "../../../../sdk-f7aba7f5.js";
import {
    c as Il,
    g as Li,
    a as Vl,
    t as Nl,
    k as Fl
} from "../../../../constants-1491974f.js";
import {
    r as u,
    R as me,
    $ as jl
} from "../../../../sdk-3856d75a.js";
import {
    c as Tt,
    a as ke,
    u as re,
    i as Bl,
    b as Ul,
    s as Wl
} from "../../../../utils-e2656ec5.js";
(function() {
    try {
        var e = typeof window < "u" ? window : typeof global < "u" ? global : typeof self < "u" ? self : {},
            t = new Error().stack;
        t && (e._sentryDebugIds = e._sentryDebugIds || {}, e._sentryDebugIds[t] = "4c6701f6-bfea-40c5-a740-44d3ada0699c", e._sentryDebugIdIdentifier = "sentry-dbid-4c6701f6-bfea-40c5-a740-44d3ada0699c")
    } catch {}
})();
var Ii = {
    exports: {}
};
(function(e, t) {
    (function(n, r) {
        r(e)
    })(typeof globalThis < "u" ? globalThis : typeof self < "u" ? self : Il, function(n) {
        if (!globalThis.chrome?.runtime?.id) throw new Error("This script should only be loaded in a browser extension.");
        if (typeof globalThis.browser > "u" || Object.getPrototypeOf(globalThis.browser) !== Object.prototype) {
            const r = "The message port closed before a response was received.",
                o = s => {
                    const i = {
                        alarms: {
                            clear: {
                                minArgs: 0,
                                maxArgs: 1
                            },
                            clearAll: {
                                minArgs: 0,
                                maxArgs: 0
                            },
                            get: {
                                minArgs: 0,
                                maxArgs: 1
                            },
                            getAll: {
                                minArgs: 0,
                                maxArgs: 0
                            }
                        },
                        bookmarks: {
                            create: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            get: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            getChildren: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            getRecent: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            getSubTree: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            getTree: {
                                minArgs: 0,
                                maxArgs: 0
                            },
                            move: {
                                minArgs: 2,
                                maxArgs: 2
                            },
                            remove: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            removeTree: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            search: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            update: {
                                minArgs: 2,
                                maxArgs: 2
                            }
                        },
                        browserAction: {
                            disable: {
                                minArgs: 0,
                                maxArgs: 1,
                                fallbackToNoCallback: !0
                            },
                            enable: {
                                minArgs: 0,
                                maxArgs: 1,
                                fallbackToNoCallback: !0
                            },
                            getBadgeBackgroundColor: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            getBadgeText: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            getPopup: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            getTitle: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            openPopup: {
                                minArgs: 0,
                                maxArgs: 0
                            },
                            setBadgeBackgroundColor: {
                                minArgs: 1,
                                maxArgs: 1,
                                fallbackToNoCallback: !0
                            },
                            setBadgeText: {
                                minArgs: 1,
                                maxArgs: 1,
                                fallbackToNoCallback: !0
                            },
                            setIcon: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            setPopup: {
                                minArgs: 1,
                                maxArgs: 1,
                                fallbackToNoCallback: !0
                            },
                            setTitle: {
                                minArgs: 1,
                                maxArgs: 1,
                                fallbackToNoCallback: !0
                            }
                        },
                        browsingData: {
                            remove: {
                                minArgs: 2,
                                maxArgs: 2
                            },
                            removeCache: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            removeCookies: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            removeDownloads: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            removeFormData: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            removeHistory: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            removeLocalStorage: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            removePasswords: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            removePluginData: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            settings: {
                                minArgs: 0,
                                maxArgs: 0
                            }
                        },
                        commands: {
                            getAll: {
                                minArgs: 0,
                                maxArgs: 0
                            }
                        },
                        contextMenus: {
                            remove: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            removeAll: {
                                minArgs: 0,
                                maxArgs: 0
                            },
                            update: {
                                minArgs: 2,
                                maxArgs: 2
                            }
                        },
                        cookies: {
                            get: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            getAll: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            getAllCookieStores: {
                                minArgs: 0,
                                maxArgs: 0
                            },
                            remove: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            set: {
                                minArgs: 1,
                                maxArgs: 1
                            }
                        },
                        devtools: {
                            inspectedWindow: {
                                eval: {
                                    minArgs: 1,
                                    maxArgs: 2,
                                    singleCallbackArg: !1
                                }
                            },
                            panels: {
                                create: {
                                    minArgs: 3,
                                    maxArgs: 3,
                                    singleCallbackArg: !0
                                },
                                elements: {
                                    createSidebarPane: {
                                        minArgs: 1,
                                        maxArgs: 1
                                    }
                                }
                            }
                        },
                        downloads: {
                            cancel: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            download: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            erase: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            getFileIcon: {
                                minArgs: 1,
                                maxArgs: 2
                            },
                            open: {
                                minArgs: 1,
                                maxArgs: 1,
                                fallbackToNoCallback: !0
                            },
                            pause: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            removeFile: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            resume: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            search: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            show: {
                                minArgs: 1,
                                maxArgs: 1,
                                fallbackToNoCallback: !0
                            }
                        },
                        extension: {
                            isAllowedFileSchemeAccess: {
                                minArgs: 0,
                                maxArgs: 0
                            },
                            isAllowedIncognitoAccess: {
                                minArgs: 0,
                                maxArgs: 0
                            }
                        },
                        history: {
                            addUrl: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            deleteAll: {
                                minArgs: 0,
                                maxArgs: 0
                            },
                            deleteRange: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            deleteUrl: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            getVisits: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            search: {
                                minArgs: 1,
                                maxArgs: 1
                            }
                        },
                        i18n: {
                            detectLanguage: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            getAcceptLanguages: {
                                minArgs: 0,
                                maxArgs: 0
                            }
                        },
                        identity: {
                            launchWebAuthFlow: {
                                minArgs: 1,
                                maxArgs: 1
                            }
                        },
                        idle: {
                            queryState: {
                                minArgs: 1,
                                maxArgs: 1
                            }
                        },
                        management: {
                            get: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            getAll: {
                                minArgs: 0,
                                maxArgs: 0
                            },
                            getSelf: {
                                minArgs: 0,
                                maxArgs: 0
                            },
                            setEnabled: {
                                minArgs: 2,
                                maxArgs: 2
                            },
                            uninstallSelf: {
                                minArgs: 0,
                                maxArgs: 1
                            }
                        },
                        notifications: {
                            clear: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            create: {
                                minArgs: 1,
                                maxArgs: 2
                            },
                            getAll: {
                                minArgs: 0,
                                maxArgs: 0
                            },
                            getPermissionLevel: {
                                minArgs: 0,
                                maxArgs: 0
                            },
                            update: {
                                minArgs: 2,
                                maxArgs: 2
                            }
                        },
                        pageAction: {
                            getPopup: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            getTitle: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            hide: {
                                minArgs: 1,
                                maxArgs: 1,
                                fallbackToNoCallback: !0
                            },
                            setIcon: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            setPopup: {
                                minArgs: 1,
                                maxArgs: 1,
                                fallbackToNoCallback: !0
                            },
                            setTitle: {
                                minArgs: 1,
                                maxArgs: 1,
                                fallbackToNoCallback: !0
                            },
                            show: {
                                minArgs: 1,
                                maxArgs: 1,
                                fallbackToNoCallback: !0
                            }
                        },
                        permissions: {
                            contains: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            getAll: {
                                minArgs: 0,
                                maxArgs: 0
                            },
                            remove: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            request: {
                                minArgs: 1,
                                maxArgs: 1
                            }
                        },
                        runtime: {
                            getBackgroundPage: {
                                minArgs: 0,
                                maxArgs: 0
                            },
                            getPlatformInfo: {
                                minArgs: 0,
                                maxArgs: 0
                            },
                            openOptionsPage: {
                                minArgs: 0,
                                maxArgs: 0
                            },
                            requestUpdateCheck: {
                                minArgs: 0,
                                maxArgs: 0
                            },
                            sendMessage: {
                                minArgs: 1,
                                maxArgs: 3
                            },
                            sendNativeMessage: {
                                minArgs: 2,
                                maxArgs: 2
                            },
                            setUninstallURL: {
                                minArgs: 1,
                                maxArgs: 1
                            }
                        },
                        sessions: {
                            getDevices: {
                                minArgs: 0,
                                maxArgs: 1
                            },
                            getRecentlyClosed: {
                                minArgs: 0,
                                maxArgs: 1
                            },
                            restore: {
                                minArgs: 0,
                                maxArgs: 1
                            }
                        },
                        storage: {
                            local: {
                                clear: {
                                    minArgs: 0,
                                    maxArgs: 0
                                },
                                get: {
                                    minArgs: 0,
                                    maxArgs: 1
                                },
                                getBytesInUse: {
                                    minArgs: 0,
                                    maxArgs: 1
                                },
                                remove: {
                                    minArgs: 1,
                                    maxArgs: 1
                                },
                                set: {
                                    minArgs: 1,
                                    maxArgs: 1
                                }
                            },
                            managed: {
                                get: {
                                    minArgs: 0,
                                    maxArgs: 1
                                },
                                getBytesInUse: {
                                    minArgs: 0,
                                    maxArgs: 1
                                }
                            },
                            sync: {
                                clear: {
                                    minArgs: 0,
                                    maxArgs: 0
                                },
                                get: {
                                    minArgs: 0,
                                    maxArgs: 1
                                },
                                getBytesInUse: {
                                    minArgs: 0,
                                    maxArgs: 1
                                },
                                remove: {
                                    minArgs: 1,
                                    maxArgs: 1
                                },
                                set: {
                                    minArgs: 1,
                                    maxArgs: 1
                                }
                            }
                        },
                        tabs: {
                            captureVisibleTab: {
                                minArgs: 0,
                                maxArgs: 2
                            },
                            create: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            detectLanguage: {
                                minArgs: 0,
                                maxArgs: 1
                            },
                            discard: {
                                minArgs: 0,
                                maxArgs: 1
                            },
                            duplicate: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            executeScript: {
                                minArgs: 1,
                                maxArgs: 2
                            },
                            get: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            getCurrent: {
                                minArgs: 0,
                                maxArgs: 0
                            },
                            getZoom: {
                                minArgs: 0,
                                maxArgs: 1
                            },
                            getZoomSettings: {
                                minArgs: 0,
                                maxArgs: 1
                            },
                            goBack: {
                                minArgs: 0,
                                maxArgs: 1
                            },
                            goForward: {
                                minArgs: 0,
                                maxArgs: 1
                            },
                            highlight: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            insertCSS: {
                                minArgs: 1,
                                maxArgs: 2
                            },
                            move: {
                                minArgs: 2,
                                maxArgs: 2
                            },
                            query: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            reload: {
                                minArgs: 0,
                                maxArgs: 2
                            },
                            remove: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            removeCSS: {
                                minArgs: 1,
                                maxArgs: 2
                            },
                            sendMessage: {
                                minArgs: 2,
                                maxArgs: 3
                            },
                            setZoom: {
                                minArgs: 1,
                                maxArgs: 2
                            },
                            setZoomSettings: {
                                minArgs: 1,
                                maxArgs: 2
                            },
                            update: {
                                minArgs: 1,
                                maxArgs: 2
                            }
                        },
                        topSites: {
                            get: {
                                minArgs: 0,
                                maxArgs: 0
                            }
                        },
                        webNavigation: {
                            getAllFrames: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            getFrame: {
                                minArgs: 1,
                                maxArgs: 1
                            }
                        },
                        webRequest: {
                            handlerBehaviorChanged: {
                                minArgs: 0,
                                maxArgs: 0
                            }
                        },
                        windows: {
                            create: {
                                minArgs: 0,
                                maxArgs: 1
                            },
                            get: {
                                minArgs: 1,
                                maxArgs: 2
                            },
                            getAll: {
                                minArgs: 0,
                                maxArgs: 1
                            },
                            getCurrent: {
                                minArgs: 0,
                                maxArgs: 1
                            },
                            getLastFocused: {
                                minArgs: 0,
                                maxArgs: 1
                            },
                            remove: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            update: {
                                minArgs: 2,
                                maxArgs: 2
                            }
                        }
                    };
                    if (Object.keys(i).length === 0) throw new Error("api-metadata.json has not been included in browser-polyfill");
                    class a extends WeakMap {
                        constructor(w, S = void 0) {
                            super(S), this.createItem = w
                        }
                        get(w) {
                            return this.has(w) || this.set(w, this.createItem(w)), super.get(w)
                        }
                    }
                    const c = A => A && typeof A == "object" && typeof A.then == "function",
                        l = (A, w) => (...S) => {
                            s.runtime.lastError ? A.reject(new Error(s.runtime.lastError.message)) : w.singleCallbackArg || S.length <= 1 && w.singleCallbackArg !== !1 ? A.resolve(S[0]) : A.resolve(S)
                        },
                        f = A => A == 1 ? "argument" : "arguments",
                        d = (A, w) => function(E, ...j) {
                            if (j.length < w.minArgs) throw new Error(`Expected at least ${w.minArgs} ${f(w.minArgs)} for ${A}(), got ${j.length}`);
                            if (j.length > w.maxArgs) throw new Error(`Expected at most ${w.maxArgs} ${f(w.maxArgs)} for ${A}(), got ${j.length}`);
                            return new Promise((I, G) => {
                                if (w.fallbackToNoCallback) try {
                                    E[A](...j, l({
                                        resolve: I,
                                        reject: G
                                    }, w))
                                } catch (_) {
                                    console.warn(`${A} API method doesn't seem to support the callback parameter, falling back to call it without a callback: `, _), E[A](...j), w.fallbackToNoCallback = !1, w.noCallback = !0, I()
                                } else w.noCallback ? (E[A](...j), I()) : E[A](...j, l({
                                    resolve: I,
                                    reject: G
                                }, w))
                            })
                        },
                        h = (A, w, S) => new Proxy(w, {
                            apply(E, j, I) {
                                return S.call(j, A, ...I)
                            }
                        });
                    let p = Function.call.bind(Object.prototype.hasOwnProperty);
                    const g = (A, w = {}, S = {}) => {
                            let E = Object.create(null),
                                j = {
                                    has(G, _) {
                                        return _ in A || _ in E
                                    },
                                    get(G, _, L) {
                                        if (_ in E) return E[_];
                                        if (!(_ in A)) return;
                                        let k = A[_];
                                        if (typeof k == "function")
                                            if (typeof w[_] == "function") k = h(A, A[_], w[_]);
                                            else if (p(S, _)) {
                                            let U = d(_, S[_]);
                                            k = h(A, A[_], U)
                                        } else k = k.bind(A);
                                        else if (typeof k == "object" && k !== null && (p(w, _) || p(S, _))) k = g(k, w[_], S[_]);
                                        else if (p(S, "*")) k = g(k, w[_], S["*"]);
                                        else return Object.defineProperty(E, _, {
                                            configurable: !0,
                                            enumerable: !0,
                                            get() {
                                                return A[_]
                                            },
                                            set(U) {
                                                A[_] = U
                                            }
                                        }), k;
                                        return E[_] = k, k
                                    },
                                    set(G, _, L, k) {
                                        return _ in E ? E[_] = L : A[_] = L, !0
                                    },
                                    defineProperty(G, _, L) {
                                        return Reflect.defineProperty(E, _, L)
                                    },
                                    deleteProperty(G, _) {
                                        return Reflect.deleteProperty(E, _)
                                    }
                                },
                                I = Object.create(A);
                            return new Proxy(I, j)
                        },
                        m = A => ({
                            addListener(w, S, ...E) {
                                w.addListener(A.get(S), ...E)
                            },
                            hasListener(w, S) {
                                return w.hasListener(A.get(S))
                            },
                            removeListener(w, S) {
                                w.removeListener(A.get(S))
                            }
                        }),
                        b = new a(A => typeof A != "function" ? A : function(S) {
                            const E = g(S, {}, {
                                getContent: {
                                    minArgs: 0,
                                    maxArgs: 0
                                }
                            });
                            A(E)
                        }),
                        x = new a(A => typeof A != "function" ? A : function(S, E, j) {
                            let I = !1,
                                G, _ = new Promise(Z => {
                                    G = function(q) {
                                        I = !0, Z(q)
                                    }
                                }),
                                L;
                            try {
                                L = A(S, E, G)
                            } catch (Z) {
                                L = Promise.reject(Z)
                            }
                            const k = L !== !0 && c(L);
                            if (L !== !0 && !k && !I) return !1;
                            const U = Z => {
                                Z.then(q => {
                                    j(q)
                                }, q => {
                                    let N;
                                    q && (q instanceof Error || typeof q.message == "string") ? N = q.message : N = "An unexpected error occurred", j({
                                        __mozWebExtensionPolyfillReject__: !0,
                                        message: N
                                    })
                                }).catch(q => {
                                    console.error("Failed to send onMessage rejected reply", q)
                                })
                            };
                            return U(k ? L : _), !0
                        }),
                        v = ({
                            reject: A,
                            resolve: w
                        }, S) => {
                            s.runtime.lastError ? s.runtime.lastError.message === r ? w() : A(new Error(s.runtime.lastError.message)) : S && S.__mozWebExtensionPolyfillReject__ ? A(new Error(S.message)) : w(S)
                        },
                        y = (A, w, S, ...E) => {
                            if (E.length < w.minArgs) throw new Error(`Expected at least ${w.minArgs} ${f(w.minArgs)} for ${A}(), got ${E.length}`);
                            if (E.length > w.maxArgs) throw new Error(`Expected at most ${w.maxArgs} ${f(w.maxArgs)} for ${A}(), got ${E.length}`);
                            return new Promise((j, I) => {
                                const G = v.bind(null, {
                                    resolve: j,
                                    reject: I
                                });
                                E.push(G), S.sendMessage(...E)
                            })
                        },
                        C = {
                            devtools: {
                                network: {
                                    onRequestFinished: m(b)
                                }
                            },
                            runtime: {
                                onMessage: m(x),
                                onMessageExternal: m(x),
                                sendMessage: y.bind(null, "sendMessage", {
                                    minArgs: 1,
                                    maxArgs: 3
                                })
                            },
                            tabs: {
                                sendMessage: y.bind(null, "sendMessage", {
                                    minArgs: 2,
                                    maxArgs: 3
                                })
                            }
                        },
                        $ = {
                            clear: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            get: {
                                minArgs: 1,
                                maxArgs: 1
                            },
                            set: {
                                minArgs: 1,
                                maxArgs: 1
                            }
                        };
                    return i.privacy = {
                        network: {
                            "*": $
                        },
                        services: {
                            "*": $
                        },
                        websites: {
                            "*": $
                        }
                    }, g(s, C, i)
                };
            n.exports = o(chrome)
        } else n.exports = globalThis.browser
    })
})(Ii);
var Hl = Ii.exports;
const Gl = Li(Hl);
async function Kl(e, t) {
    const n = createDivWithId("sgreen_recorder"),
        r = n.attachShadow({
            mode: "open"
        }),
        o = document.createElement("div");
    e.forEach(s => {
        const i = document.createElement("link");
        i.setAttribute("rel", "stylesheet"), i.setAttribute("href", Gl.runtime.getURL(s)), r.appendChild(i)
    }), r.appendChild(o), document.body.appendChild(n), r.addEventListener("focusin", () => {
        const s = new FocusEvent("focusin");
        document.dispatchEvent(s)
    }), t(r)
}
const Vi = u.createContext({
        transformPagePoint: e => e,
        isStatic: !1,
        reducedMotion: "never"
    }),
    zn = u.createContext({}),
    Yn = u.createContext(null),
    Xn = typeof document < "u",
    Dn = Xn ? u.useLayoutEffect : u.useEffect,
    Ni = u.createContext({
        strict: !1
    });

function zl(e, t, n, r) {
    const {
        visualElement: o
    } = u.useContext(zn), s = u.useContext(Ni), i = u.useContext(Yn), a = u.useContext(Vi).reducedMotion, c = u.useRef();
    r = r || s.renderer, !c.current && r && (c.current = r(e, {
        visualState: t,
        parent: o,
        props: n,
        presenceContext: i,
        blockInitialAnimation: i ? i.initial === !1 : !1,
        reducedMotionConfig: a
    }));
    const l = c.current;
    return u.useInsertionEffect(() => {
        l && l.update(n, i)
    }), Dn(() => {
        l && l.render()
    }), u.useEffect(() => {
        l && l.updateFeatures()
    }), (window.HandoffAppearAnimations ? Dn : u.useEffect)(() => {
        l && l.animationState && l.animationState.animateChanges()
    }), l
}

function Ct(e) {
    return typeof e == "object" && Object.prototype.hasOwnProperty.call(e, "current")
}

function Yl(e, t, n) {
    return u.useCallback(r => {
        r && e.mount && e.mount(r), t && (r ? t.mount(r) : t.unmount()), n && (typeof n == "function" ? n(r) : Ct(n) && (n.current = r))
    }, [t])
}

function Zt(e) {
    return typeof e == "string" || Array.isArray(e)
}

function Zn(e) {
    return typeof e == "object" && typeof e.start == "function"
}
const fo = ["animate", "whileInView", "whileFocus", "whileHover", "whileTap", "whileDrag", "exit"],
    ho = ["initial", ...fo];

function qn(e) {
    return Zn(e.animate) || ho.some(t => Zt(e[t]))
}

function Fi(e) {
    return !!(qn(e) || e.variants)
}

function Xl(e, t) {
    if (qn(e)) {
        const {
            initial: n,
            animate: r
        } = e;
        return {
            initial: n === !1 || Zt(n) ? n : void 0,
            animate: Zt(r) ? r : void 0
        }
    }
    return e.inherit !== !1 ? t : {}
}

function Zl(e) {
    const {
        initial: t,
        animate: n
    } = Xl(e, u.useContext(zn));
    return u.useMemo(() => ({
        initial: t,
        animate: n
    }), [Qo(t), Qo(n)])
}

function Qo(e) {
    return Array.isArray(e) ? e.join(" ") : e
}
const Jo = {
        animation: ["animate", "variants", "whileHover", "whileTap", "exit", "whileInView", "whileFocus", "whileDrag"],
        exit: ["exit"],
        drag: ["drag", "dragControls"],
        focus: ["whileFocus"],
        hover: ["whileHover", "onHoverStart", "onHoverEnd"],
        tap: ["whileTap", "onTap", "onTapStart", "onTapCancel"],
        pan: ["onPan", "onPanStart", "onPanSessionStart", "onPanEnd"],
        inView: ["whileInView", "onViewportEnter", "onViewportLeave"],
        layout: ["layout", "layoutId"]
    },
    qt = {};
for (const e in Jo) qt[e] = {
    isEnabled: t => Jo[e].some(n => !!t[n])
};

function ql(e) {
    for (const t in e) qt[t] = {
        ...qt[t],
        ...e[t]
    }
}
const po = u.createContext({}),
    ji = u.createContext({}),
    Ql = Symbol.for("motionComponentSymbol");

function Jl({
    preloadedFeatures: e,
    createVisualElement: t,
    useRender: n,
    useVisualState: r,
    Component: o
}) {
    e && ql(e);

    function s(a, c) {
        let l;
        const f = {
                ...u.useContext(Vi),
                ...a,
                layoutId: eu(a)
            },
            {
                isStatic: d
            } = f,
            h = Zl(a),
            p = r(a, d);
        if (!d && Xn) {
            h.visualElement = zl(o, p, f, t);
            const g = u.useContext(ji),
                m = u.useContext(Ni).strict;
            h.visualElement && (l = h.visualElement.loadFeatures(f, m, e, g))
        }
        return u.createElement(zn.Provider, {
            value: h
        }, l && h.visualElement ? u.createElement(l, {
            visualElement: h.visualElement,
            ...f
        }) : null, n(o, a, Yl(p, h.visualElement, c), p, d, h.visualElement))
    }
    const i = u.forwardRef(s);
    return i[Ql] = o, i
}

function eu({
    layoutId: e
}) {
    const t = u.useContext(po).id;
    return t && e !== void 0 ? t + "-" + e : e
}

function tu(e) {
    function t(r, o = {}) {
        return Jl(e(r, o))
    }
    if (typeof Proxy > "u") return t;
    const n = new Map;
    return new Proxy(t, {
        get: (r, o) => (n.has(o) || n.set(o, t(o)), n.get(o))
    })
}
const nu = ["animate", "circle", "defs", "desc", "ellipse", "g", "image", "line", "filter", "marker", "mask", "metadata", "path", "pattern", "polygon", "polyline", "rect", "stop", "switch", "symbol", "svg", "text", "tspan", "use", "view"];

function mo(e) {
    return typeof e != "string" || e.includes("-") ? !1 : !!(nu.indexOf(e) > -1 || /[A-Z]/.test(e))
}
const Rn = {};

function ru(e) {
    Object.assign(Rn, e)
}
const sn = ["transformPerspective", "x", "y", "z", "translateX", "translateY", "translateZ", "scale", "scaleX", "scaleY", "rotate", "rotateX", "rotateY", "rotateZ", "skew", "skewX", "skewY"],
    dt = new Set(sn);

function Bi(e, {
    layout: t,
    layoutId: n
}) {
    return dt.has(e) || e.startsWith("origin") || (t || n !== void 0) && (!!Rn[e] || e === "opacity")
}
const fe = e => !!(e && e.getVelocity),
    ou = {
        x: "translateX",
        y: "translateY",
        z: "translateZ",
        transformPerspective: "perspective"
    },
    su = sn.length;

function iu(e, {
    enableHardwareAcceleration: t = !0,
    allowTransformNone: n = !0
}, r, o) {
    let s = "";
    for (let i = 0; i < su; i++) {
        const a = sn[i];
        if (e[a] !== void 0) {
            const c = ou[a] || a;
            s += `${c}(${e[a]}) `
        }
    }
    return t && !e.z && (s += "translateZ(0)"), s = s.trim(), o ? s = o(e, r ? "" : s) : n && r && (s = "none"), s
}
const Ui = e => t => typeof t == "string" && t.startsWith(e),
    Wi = Ui("--"),
    jr = Ui("var(--"),
    au = /var\s*\(\s*--[\w-]+(\s*,\s*(?:(?:[^)(]|\((?:[^)(]+|\([^)(]*\))*\))*)+)?\s*\)/g,
    cu = (e, t) => t && typeof e == "number" ? t.transform(e) : e,
    Qe = (e, t, n) => Math.min(Math.max(n, e), t),
    ht = {
        test: e => typeof e == "number",
        parse: parseFloat,
        transform: e => e
    },
    Gt = {
        ...ht,
        transform: e => Qe(0, 1, e)
    },
    hn = {
        ...ht,
        default: 1
    },
    Kt = e => Math.round(e * 1e5) / 1e5,
    Qn = /(-)?([\d]*\.?[\d])+/g,
    Hi = /(#[0-9a-f]{3,8}|(rgb|hsl)a?\((-?[\d\.]+%?[,\s]+){2}(-?[\d\.]+%?)\s*[\,\/]?\s*[\d\.]*%?\))/gi,
    lu = /^(#[0-9a-f]{3,8}|(rgb|hsl)a?\((-?[\d\.]+%?[,\s]+){2}(-?[\d\.]+%?)\s*[\,\/]?\s*[\d\.]*%?\))$/i;

function an(e) {
    return typeof e == "string"
}
const cn = e => ({
        test: t => an(t) && t.endsWith(e) && t.split(" ").length === 1,
        parse: parseFloat,
        transform: t => `${t}${e}`
    }),
    ze = cn("deg"),
    Oe = cn("%"),
    B = cn("px"),
    uu = cn("vh"),
    fu = cn("vw"),
    es = {
        ...Oe,
        parse: e => Oe.parse(e) / 100,
        transform: e => Oe.transform(e * 100)
    },
    ts = {
        ...ht,
        transform: Math.round
    },
    Gi = {
        borderWidth: B,
        borderTopWidth: B,
        borderRightWidth: B,
        borderBottomWidth: B,
        borderLeftWidth: B,
        borderRadius: B,
        radius: B,
        borderTopLeftRadius: B,
        borderTopRightRadius: B,
        borderBottomRightRadius: B,
        borderBottomLeftRadius: B,
        width: B,
        maxWidth: B,
        height: B,
        maxHeight: B,
        size: B,
        top: B,
        right: B,
        bottom: B,
        left: B,
        padding: B,
        paddingTop: B,
        paddingRight: B,
        paddingBottom: B,
        paddingLeft: B,
        margin: B,
        marginTop: B,
        marginRight: B,
        marginBottom: B,
        marginLeft: B,
        rotate: ze,
        rotateX: ze,
        rotateY: ze,
        rotateZ: ze,
        scale: hn,
        scaleX: hn,
        scaleY: hn,
        scaleZ: hn,
        skew: ze,
        skewX: ze,
        skewY: ze,
        distance: B,
        translateX: B,
        translateY: B,
        translateZ: B,
        x: B,
        y: B,
        z: B,
        perspective: B,
        transformPerspective: B,
        opacity: Gt,
        originX: es,
        originY: es,
        originZ: B,
        zIndex: ts,
        fillOpacity: Gt,
        strokeOpacity: Gt,
        numOctaves: ts
    };

function go(e, t, n, r) {
    const {
        style: o,
        vars: s,
        transform: i,
        transformOrigin: a
    } = e;
    let c = !1,
        l = !1,
        f = !0;
    for (const d in t) {
        const h = t[d];
        if (Wi(d)) {
            s[d] = h;
            continue
        }
        const p = Gi[d],
            g = cu(h, p);
        if (dt.has(d)) {
            if (c = !0, i[d] = g, !f) continue;
            h !== (p.default || 0) && (f = !1)
        } else d.startsWith("origin") ? (l = !0, a[d] = g) : o[d] = g
    }
    if (t.transform || (c || r ? o.transform = iu(e.transform, n, f, r) : o.transform && (o.transform = "none")), l) {
        const {
            originX: d = "50%",
            originY: h = "50%",
            originZ: p = 0
        } = a;
        o.transformOrigin = `${d} ${h} ${p}`
    }
}
const vo = () => ({
    style: {},
    transform: {},
    transformOrigin: {},
    vars: {}
});

function Ki(e, t, n) {
    for (const r in t) !fe(t[r]) && !Bi(r, n) && (e[r] = t[r])
}

function du({
    transformTemplate: e
}, t, n) {
    return u.useMemo(() => {
        const r = vo();
        return go(r, t, {
            enableHardwareAcceleration: !n
        }, e), Object.assign({}, r.vars, r.style)
    }, [t])
}

function hu(e, t, n) {
    const r = e.style || {},
        o = {};
    return Ki(o, r, e), Object.assign(o, du(e, t, n)), e.transformValues ? e.transformValues(o) : o
}

function pu(e, t, n) {
    const r = {},
        o = hu(e, t, n);
    return e.drag && e.dragListener !== !1 && (r.draggable = !1, o.userSelect = o.WebkitUserSelect = o.WebkitTouchCallout = "none", o.touchAction = e.drag === !0 ? "none" : `pan-${e.drag==="x"?"y":"x"}`), e.tabIndex === void 0 && (e.onTap || e.onTapStart || e.whileTap) && (r.tabIndex = 0), r.style = o, r
}
const mu = new Set(["animate", "exit", "variants", "initial", "style", "values", "variants", "transition", "transformTemplate", "transformValues", "custom", "inherit", "onLayoutAnimationStart", "onLayoutAnimationComplete", "onLayoutMeasure", "onBeforeLayoutMeasure", "onAnimationStart", "onAnimationComplete", "onUpdate", "onDragStart", "onDrag", "onDragEnd", "onMeasureDragConstraints", "onDirectionLock", "onDragTransitionEnd", "_dragX", "_dragY", "onHoverStart", "onHoverEnd", "onViewportEnter", "onViewportLeave", "ignoreStrict", "viewport"]);

function On(e) {
    return e.startsWith("while") || e.startsWith("drag") && e !== "draggable" || e.startsWith("layout") || e.startsWith("onTap") || e.startsWith("onPan") || mu.has(e)
}
let zi = e => !On(e);

function gu(e) {
    e && (zi = t => t.startsWith("on") ? !On(t) : e(t))
}
try {
    gu(require("@emotion/is-prop-valid").default)
} catch {}

function vu(e, t, n) {
    const r = {};
    for (const o in e) o === "values" && typeof e.values == "object" || (zi(o) || n === !0 && On(o) || !t && !On(o) || e.draggable && o.startsWith("onDrag")) && (r[o] = e[o]);
    return r
}

function ns(e, t, n) {
    return typeof e == "string" ? e : B.transform(t + n * e)
}

function yu(e, t, n) {
    const r = ns(t, e.x, e.width),
        o = ns(n, e.y, e.height);
    return `${r} ${o}`
}
const bu = {
        offset: "stroke-dashoffset",
        array: "stroke-dasharray"
    },
    xu = {
        offset: "strokeDashoffset",
        array: "strokeDasharray"
    };

function wu(e, t, n = 1, r = 0, o = !0) {
    e.pathLength = 1;
    const s = o ? bu : xu;
    e[s.offset] = B.transform(-r);
    const i = B.transform(t),
        a = B.transform(n);
    e[s.array] = `${i} ${a}`
}

function yo(e, {
    attrX: t,
    attrY: n,
    attrScale: r,
    originX: o,
    originY: s,
    pathLength: i,
    pathSpacing: a = 1,
    pathOffset: c = 0,
    ...l
}, f, d, h) {
    if (go(e, l, f, h), d) {
        e.style.viewBox && (e.attrs.viewBox = e.style.viewBox);
        return
    }
    e.attrs = e.style, e.style = {};
    const {
        attrs: p,
        style: g,
        dimensions: m
    } = e;
    p.transform && (m && (g.transform = p.transform), delete p.transform), m && (o !== void 0 || s !== void 0 || g.transform) && (g.transformOrigin = yu(m, o !== void 0 ? o : .5, s !== void 0 ? s : .5)), t !== void 0 && (p.x = t), n !== void 0 && (p.y = n), r !== void 0 && (p.scale = r), i !== void 0 && wu(p, i, a, c, !1)
}
const Yi = () => ({
        ...vo(),
        attrs: {}
    }),
    bo = e => typeof e == "string" && e.toLowerCase() === "svg";

function Cu(e, t, n, r) {
    const o = u.useMemo(() => {
        const s = Yi();
        return yo(s, t, {
            enableHardwareAcceleration: !1
        }, bo(r), e.transformTemplate), {
            ...s.attrs,
            style: {
                ...s.style
            }
        }
    }, [t]);
    if (e.style) {
        const s = {};
        Ki(s, e.style, e), o.style = {
            ...s,
            ...o.style
        }
    }
    return o
}

function Au(e = !1) {
    return (n, r, o, {
        latestValues: s
    }, i) => {
        const c = (mo(n) ? Cu : pu)(r, s, i, n),
            f = {
                ...vu(r, typeof n == "string", e),
                ...c,
                ref: o
            },
            {
                children: d
            } = r,
            h = u.useMemo(() => fe(d) ? d.get() : d, [d]);
        return u.createElement(n, {
            ...f,
            children: h
        })
    }
}
const xo = e => e.replace(/([a-z])([A-Z])/g, "$1-$2").toLowerCase();

function Xi(e, {
    style: t,
    vars: n
}, r, o) {
    Object.assign(e.style, t, o && o.getProjectionStyles(r));
    for (const s in n) e.style.setProperty(s, n[s])
}
const Zi = new Set(["baseFrequency", "diffuseConstant", "kernelMatrix", "kernelUnitLength", "keySplines", "keyTimes", "limitingConeAngle", "markerHeight", "markerWidth", "numOctaves", "targetX", "targetY", "surfaceScale", "specularConstant", "specularExponent", "stdDeviation", "tableValues", "viewBox", "gradientTransform", "pathLength", "startOffset", "textLength", "lengthAdjust"]);

function qi(e, t, n, r) {
    Xi(e, t, void 0, r);
    for (const o in t.attrs) e.setAttribute(Zi.has(o) ? o : xo(o), t.attrs[o])
}

function wo(e, t) {
    const {
        style: n
    } = e, r = {};
    for (const o in n)(fe(n[o]) || t.style && fe(t.style[o]) || Bi(o, e)) && (r[o] = n[o]);
    return r
}

function Qi(e, t) {
    const n = wo(e, t);
    for (const r in e)
        if (fe(e[r]) || fe(t[r])) {
            const o = sn.indexOf(r) !== -1 ? "attr" + r.charAt(0).toUpperCase() + r.substring(1) : r;
            n[o] = e[r]
        } return n
}

function Co(e, t, n, r = {}, o = {}) {
    return typeof t == "function" && (t = t(n !== void 0 ? n : e.custom, r, o)), typeof t == "string" && (t = e.variants && e.variants[t]), typeof t == "function" && (t = t(n !== void 0 ? n : e.custom, r, o)), t
}

function Ji(e) {
    const t = u.useRef(null);
    return t.current === null && (t.current = e()), t.current
}
const _n = e => Array.isArray(e),
    $u = e => !!(e && typeof e == "object" && e.mix && e.toValue),
    Pu = e => _n(e) ? e[e.length - 1] || 0 : e;

function Pn(e) {
    const t = fe(e) ? e.get() : e;
    return $u(t) ? t.toValue() : t
}

function Su({
    scrapeMotionValuesFromProps: e,
    createRenderState: t,
    onMount: n
}, r, o, s) {
    const i = {
        latestValues: Eu(r, o, s, e),
        renderState: t()
    };
    return n && (i.mount = a => n(r, a, i)), i
}
const ea = e => (t, n) => {
    const r = u.useContext(zn),
        o = u.useContext(Yn),
        s = () => Su(e, t, r, o);
    return n ? s() : Ji(s)
};

function Eu(e, t, n, r) {
    const o = {},
        s = r(e, {});
    for (const h in s) o[h] = Pn(s[h]);
    let {
        initial: i,
        animate: a
    } = e;
    const c = qn(e),
        l = Fi(e);
    t && l && !c && e.inherit !== !1 && (i === void 0 && (i = t.initial), a === void 0 && (a = t.animate));
    let f = n ? n.initial === !1 : !1;
    f = f || i === !1;
    const d = f ? a : i;
    return d && typeof d != "boolean" && !Zn(d) && (Array.isArray(d) ? d : [d]).forEach(p => {
        const g = Co(e, p);
        if (!g) return;
        const {
            transitionEnd: m,
            transition: b,
            ...x
        } = g;
        for (const v in x) {
            let y = x[v];
            if (Array.isArray(y)) {
                const C = f ? y.length - 1 : 0;
                y = y[C]
            }
            y !== null && (o[v] = y)
        }
        for (const v in m) o[v] = m[v]
    }), o
}
const Tu = {
        useVisualState: ea({
            scrapeMotionValuesFromProps: Qi,
            createRenderState: Yi,
            onMount: (e, t, {
                renderState: n,
                latestValues: r
            }) => {
                try {
                    n.dimensions = typeof t.getBBox == "function" ? t.getBBox() : t.getBoundingClientRect()
                } catch {
                    n.dimensions = {
                        x: 0,
                        y: 0,
                        width: 0,
                        height: 0
                    }
                }
                yo(n, r, {
                    enableHardwareAcceleration: !1
                }, bo(t.tagName), e.transformTemplate), qi(t, n)
            }
        })
    },
    Mu = {
        useVisualState: ea({
            scrapeMotionValuesFromProps: wo,
            createRenderState: vo
        })
    };

function Du(e, {
    forwardMotionProps: t = !1
}, n, r) {
    return {
        ...mo(e) ? Tu : Mu,
        preloadedFeatures: n,
        useRender: Au(t),
        createVisualElement: r,
        Component: e
    }
}

function Ve(e, t, n, r = {
    passive: !0
}) {
    return e.addEventListener(t, n, r), () => e.removeEventListener(t, n)
}
const ta = e => e.pointerType === "mouse" ? typeof e.button != "number" || e.button <= 0 : e.isPrimary !== !1;

function Jn(e, t = "page") {
    return {
        point: {
            x: e[t + "X"],
            y: e[t + "Y"]
        }
    }
}
const Ru = e => t => ta(t) && e(t, Jn(t));

function Ne(e, t, n, r) {
    return Ve(e, t, Ru(n), r)
}
const Ou = (e, t) => n => t(e(n)),
    Ze = (...e) => e.reduce(Ou);

function na(e) {
    let t = null;
    return () => {
        const n = () => {
            t = null
        };
        return t === null ? (t = e, n) : !1
    }
}
const rs = na("dragHorizontal"),
    os = na("dragVertical");

function ra(e) {
    let t = !1;
    if (e === "y") t = os();
    else if (e === "x") t = rs();
    else {
        const n = rs(),
            r = os();
        n && r ? t = () => {
            n(), r()
        } : (n && n(), r && r())
    }
    return t
}

function oa() {
    const e = ra(!0);
    return e ? (e(), !1) : !0
}
class rt {
    constructor(t) {
        this.isMounted = !1, this.node = t
    }
    update() {}
}
const ee = e => e;

function _u(e) {
    let t = [],
        n = [],
        r = 0,
        o = !1,
        s = !1;
    const i = new WeakSet,
        a = {
            schedule: (c, l = !1, f = !1) => {
                const d = f && o,
                    h = d ? t : n;
                return l && i.add(c), h.indexOf(c) === -1 && (h.push(c), d && o && (r = t.length)), c
            },
            cancel: c => {
                const l = n.indexOf(c);
                l !== -1 && n.splice(l, 1), i.delete(c)
            },
            process: c => {
                if (o) {
                    s = !0;
                    return
                }
                if (o = !0, [t, n] = [n, t], n.length = 0, r = t.length, r)
                    for (let l = 0; l < r; l++) {
                        const f = t[l];
                        f(c), i.has(f) && (a.schedule(f), e())
                    }
                o = !1, s && (s = !1, a.process(c))
            }
        };
    return a
}
const pn = ["prepare", "read", "update", "preRender", "render", "postRender"],
    ku = 40;

function Lu(e, t) {
    let n = !1,
        r = !0;
    const o = {
            delta: 0,
            timestamp: 0,
            isProcessing: !1
        },
        s = pn.reduce((d, h) => (d[h] = _u(() => n = !0), d), {}),
        i = d => s[d].process(o),
        a = () => {
            const d = performance.now();
            n = !1, o.delta = r ? 1e3 / 60 : Math.max(Math.min(d - o.timestamp, ku), 1), o.timestamp = d, o.isProcessing = !0, pn.forEach(i), o.isProcessing = !1, n && t && (r = !1, e(a))
        },
        c = () => {
            n = !0, r = !0, o.isProcessing || e(a)
        };
    return {
        schedule: pn.reduce((d, h) => {
            const p = s[h];
            return d[h] = (g, m = !1, b = !1) => (n || c(), p.schedule(g, m, b)), d
        }, {}),
        cancel: d => pn.forEach(h => s[h].cancel(d)),
        state: o,
        steps: s
    }
}
const {
    schedule: Q,
    cancel: je,
    state: ie,
    steps: gr
} = Lu(typeof requestAnimationFrame < "u" ? requestAnimationFrame : ee, !0);

function ss(e, t) {
    const n = "pointer" + (t ? "enter" : "leave"),
        r = "onHover" + (t ? "Start" : "End"),
        o = (s, i) => {
            if (s.type === "touch" || oa()) return;
            const a = e.getProps();
            e.animationState && a.whileHover && e.animationState.setActive("whileHover", t), a[r] && Q.update(() => a[r](s, i))
        };
    return Ne(e.current, n, o, {
        passive: !e.getProps()[r]
    })
}
class Iu extends rt {
    mount() {
        this.unmount = Ze(ss(this.node, !0), ss(this.node, !1))
    }
    unmount() {}
}
class Vu extends rt {
    constructor() {
        super(...arguments), this.isActive = !1
    }
    onFocus() {
        let t = !1;
        try {
            t = this.node.current.matches(":focus-visible")
        } catch {
            t = !0
        }!t || !this.node.animationState || (this.node.animationState.setActive("whileFocus", !0), this.isActive = !0)
    }
    onBlur() {
        !this.isActive || !this.node.animationState || (this.node.animationState.setActive("whileFocus", !1), this.isActive = !1)
    }
    mount() {
        this.unmount = Ze(Ve(this.node.current, "focus", () => this.onFocus()), Ve(this.node.current, "blur", () => this.onBlur()))
    }
    unmount() {}
}
const sa = (e, t) => t ? e === t ? !0 : sa(e, t.parentElement) : !1;

function vr(e, t) {
    if (!t) return;
    const n = new PointerEvent("pointer" + e);
    t(n, Jn(n))
}
class Nu extends rt {
    constructor() {
        super(...arguments), this.removeStartListeners = ee, this.removeEndListeners = ee, this.removeAccessibleListeners = ee, this.startPointerPress = (t, n) => {
            if (this.removeEndListeners(), this.isPressing) return;
            const r = this.node.getProps(),
                s = Ne(window, "pointerup", (a, c) => {
                    if (!this.checkPressEnd()) return;
                    const {
                        onTap: l,
                        onTapCancel: f
                    } = this.node.getProps();
                    Q.update(() => {
                        sa(this.node.current, a.target) ? l && l(a, c) : f && f(a, c)
                    })
                }, {
                    passive: !(r.onTap || r.onPointerUp)
                }),
                i = Ne(window, "pointercancel", (a, c) => this.cancelPress(a, c), {
                    passive: !(r.onTapCancel || r.onPointerCancel)
                });
            this.removeEndListeners = Ze(s, i), this.startPress(t, n)
        }, this.startAccessiblePress = () => {
            const t = s => {
                    if (s.key !== "Enter" || this.isPressing) return;
                    const i = a => {
                        a.key !== "Enter" || !this.checkPressEnd() || vr("up", (c, l) => {
                            const {
                                onTap: f
                            } = this.node.getProps();
                            f && Q.update(() => f(c, l))
                        })
                    };
                    this.removeEndListeners(), this.removeEndListeners = Ve(this.node.current, "keyup", i), vr("down", (a, c) => {
                        this.startPress(a, c)
                    })
                },
                n = Ve(this.node.current, "keydown", t),
                r = () => {
                    this.isPressing && vr("cancel", (s, i) => this.cancelPress(s, i))
                },
                o = Ve(this.node.current, "blur", r);
            this.removeAccessibleListeners = Ze(n, o)
        }
    }
    startPress(t, n) {
        this.isPressing = !0;
        const {
            onTapStart: r,
            whileTap: o
        } = this.node.getProps();
        o && this.node.animationState && this.node.animationState.setActive("whileTap", !0), r && Q.update(() => r(t, n))
    }
    checkPressEnd() {
        return this.removeEndListeners(), this.isPressing = !1, this.node.getProps().whileTap && this.node.animationState && this.node.animationState.setActive("whileTap", !1), !oa()
    }
    cancelPress(t, n) {
        if (!this.checkPressEnd()) return;
        const {
            onTapCancel: r
        } = this.node.getProps();
        r && Q.update(() => r(t, n))
    }
    mount() {
        const t = this.node.getProps(),
            n = Ne(this.node.current, "pointerdown", this.startPointerPress, {
                passive: !(t.onTapStart || t.onPointerStart)
            }),
            r = Ve(this.node.current, "focus", this.startAccessiblePress);
        this.removeStartListeners = Ze(n, r)
    }
    unmount() {
        this.removeStartListeners(), this.removeEndListeners(), this.removeAccessibleListeners()
    }
}
const Br = new WeakMap,
    yr = new WeakMap,
    Fu = e => {
        const t = Br.get(e.target);
        t && t(e)
    },
    ju = e => {
        e.forEach(Fu)
    };

function Bu({
    root: e,
    ...t
}) {
    const n = e || document;
    yr.has(n) || yr.set(n, {});
    const r = yr.get(n),
        o = JSON.stringify(t);
    return r[o] || (r[o] = new IntersectionObserver(ju, {
        root: e,
        ...t
    })), r[o]
}

function Uu(e, t, n) {
    const r = Bu(t);
    return Br.set(e, n), r.observe(e), () => {
        Br.delete(e), r.unobserve(e)
    }
}
const Wu = {
    some: 0,
    all: 1
};
class Hu extends rt {
    constructor() {
        super(...arguments), this.hasEnteredView = !1, this.isInView = !1
    }
    startObserver() {
        this.unmount();
        const {
            viewport: t = {}
        } = this.node.getProps(), {
            root: n,
            margin: r,
            amount: o = "some",
            once: s
        } = t, i = {
            root: n ? n.current : void 0,
            rootMargin: r,
            threshold: typeof o == "number" ? o : Wu[o]
        }, a = c => {
            const {
                isIntersecting: l
            } = c;
            if (this.isInView === l || (this.isInView = l, s && !l && this.hasEnteredView)) return;
            l && (this.hasEnteredView = !0), this.node.animationState && this.node.animationState.setActive("whileInView", l);
            const {
                onViewportEnter: f,
                onViewportLeave: d
            } = this.node.getProps(), h = l ? f : d;
            h && h(c)
        };
        return Uu(this.node.current, i, a)
    }
    mount() {
        this.startObserver()
    }
    update() {
        if (typeof IntersectionObserver > "u") return;
        const {
            props: t,
            prevProps: n
        } = this.node;
        ["amount", "margin", "root"].some(Gu(t, n)) && this.startObserver()
    }
    unmount() {}
}

function Gu({
    viewport: e = {}
}, {
    viewport: t = {}
} = {}) {
    return n => e[n] !== t[n]
}
const Ku = {
    inView: {
        Feature: Hu
    },
    tap: {
        Feature: Nu
    },
    focus: {
        Feature: Vu
    },
    hover: {
        Feature: Iu
    }
};

function ia(e, t) {
    if (!Array.isArray(t)) return !1;
    const n = t.length;
    if (n !== e.length) return !1;
    for (let r = 0; r < n; r++)
        if (t[r] !== e[r]) return !1;
    return !0
}

function zu(e) {
    const t = {};
    return e.values.forEach((n, r) => t[r] = n.get()), t
}

function Yu(e) {
    const t = {};
    return e.values.forEach((n, r) => t[r] = n.getVelocity()), t
}

function er(e, t, n) {
    const r = e.getProps();
    return Co(r, t, n !== void 0 ? n : r.custom, zu(e), Yu(e))
}
const Xu = "framerAppearId",
    Zu = "data-" + xo(Xu);
let qu = ee,
    Ao = ee;
const qe = e => e * 1e3,
    Fe = e => e / 1e3,
    Qu = {
        current: !1
    },
    aa = e => Array.isArray(e) && typeof e[0] == "number";

function ca(e) {
    return !!(!e || typeof e == "string" && la[e] || aa(e) || Array.isArray(e) && e.every(ca))
}
const Wt = ([e, t, n, r]) => `cubic-bezier(${e}, ${t}, ${n}, ${r})`,
    la = {
        linear: "linear",
        ease: "ease",
        easeIn: "ease-in",
        easeOut: "ease-out",
        easeInOut: "ease-in-out",
        circIn: Wt([0, .65, .55, 1]),
        circOut: Wt([.55, 0, 1, .45]),
        backIn: Wt([.31, .01, .66, -.59]),
        backOut: Wt([.33, 1.53, .69, .99])
    };

function ua(e) {
    if (e) return aa(e) ? Wt(e) : Array.isArray(e) ? e.map(ua) : la[e]
}

function Ju(e, t, n, {
    delay: r = 0,
    duration: o,
    repeat: s = 0,
    repeatType: i = "loop",
    ease: a,
    times: c
} = {}) {
    const l = {
        [t]: n
    };
    c && (l.offset = c);
    const f = ua(a);
    return Array.isArray(f) && (l.easing = f), e.animate(l, {
        delay: r,
        duration: o,
        easing: Array.isArray(f) ? "linear" : f,
        fill: "both",
        iterations: s + 1,
        direction: i === "reverse" ? "alternate" : "normal"
    })
}

function ef(e, {
    repeat: t,
    repeatType: n = "loop"
}) {
    const r = t && n !== "loop" && t % 2 === 1 ? 0 : e.length - 1;
    return e[r]
}
const fa = (e, t, n) => (((1 - 3 * n + 3 * t) * e + (3 * n - 6 * t)) * e + 3 * t) * e,
    tf = 1e-7,
    nf = 12;

function rf(e, t, n, r, o) {
    let s, i, a = 0;
    do i = t + (n - t) / 2, s = fa(i, r, o) - e, s > 0 ? n = i : t = i; while (Math.abs(s) > tf && ++a < nf);
    return i
}

function ln(e, t, n, r) {
    if (e === t && n === r) return ee;
    const o = s => rf(s, 0, 1, e, n);
    return s => s === 0 || s === 1 ? s : fa(o(s), t, r)
}
const of = ln(.42, 0, 1, 1), sf = ln(0, 0, .58, 1), da = ln(.42, 0, .58, 1), af = e => Array.isArray(e) && typeof e[0] != "number", ha = e => t => t <= .5 ? e(2 * t) / 2 : (2 - e(2 * (1 - t))) / 2, pa = e => t => 1 - e(1 - t), ma = e => 1 - Math.sin(Math.acos(e)), $o = pa(ma), cf = ha($o), ga = ln(.33, 1.53, .69, .99), Po = pa(ga), lf = ha(Po), uf = e => (e *= 2) < 1 ? .5 * Po(e) : .5 * (2 - Math.pow(2, -10 * (e - 1))), ff = {
    linear: ee,
    easeIn: of,
    easeInOut: da,
    easeOut: sf,
    circIn: ma,
    circInOut: cf,
    circOut: $o,
    backIn: Po,
    backInOut: lf,
    backOut: ga,
    anticipate: uf
}, is = e => {
    if (Array.isArray(e)) {
        Ao(e.length === 4);
        const [t, n, r, o] = e;
        return ln(t, n, r, o)
    } else if (typeof e == "string") return ff[e];
    return e
}, So = (e, t) => n => !!(an(n) && lu.test(n) && n.startsWith(e) || t && Object.prototype.hasOwnProperty.call(n, t)), va = (e, t, n) => r => {
    if (!an(r)) return r;
    const [o, s, i, a] = r.match(Qn);
    return {
        [e]: parseFloat(o),
        [t]: parseFloat(s),
        [n]: parseFloat(i),
        alpha: a !== void 0 ? parseFloat(a) : 1
    }
}, df = e => Qe(0, 255, e), br = {
    ...ht,
    transform: e => Math.round(df(e))
}, ut = {
    test: So("rgb", "red"),
    parse: va("red", "green", "blue"),
    transform: ({
        red: e,
        green: t,
        blue: n,
        alpha: r = 1
    }) => "rgba(" + br.transform(e) + ", " + br.transform(t) + ", " + br.transform(n) + ", " + Kt(Gt.transform(r)) + ")"
};

function hf(e) {
    let t = "",
        n = "",
        r = "",
        o = "";
    return e.length > 5 ? (t = e.substring(1, 3), n = e.substring(3, 5), r = e.substring(5, 7), o = e.substring(7, 9)) : (t = e.substring(1, 2), n = e.substring(2, 3), r = e.substring(3, 4), o = e.substring(4, 5), t += t, n += n, r += r, o += o), {
        red: parseInt(t, 16),
        green: parseInt(n, 16),
        blue: parseInt(r, 16),
        alpha: o ? parseInt(o, 16) / 255 : 1
    }
}
const Ur = {
        test: So("#"),
        parse: hf,
        transform: ut.transform
    },
    At = {
        test: So("hsl", "hue"),
        parse: va("hue", "saturation", "lightness"),
        transform: ({
            hue: e,
            saturation: t,
            lightness: n,
            alpha: r = 1
        }) => "hsla(" + Math.round(e) + ", " + Oe.transform(Kt(t)) + ", " + Oe.transform(Kt(n)) + ", " + Kt(Gt.transform(r)) + ")"
    },
    ce = {
        test: e => ut.test(e) || Ur.test(e) || At.test(e),
        parse: e => ut.test(e) ? ut.parse(e) : At.test(e) ? At.parse(e) : Ur.parse(e),
        transform: e => an(e) ? e : e.hasOwnProperty("red") ? ut.transform(e) : At.transform(e)
    },
    J = (e, t, n) => -n * e + n * t + e;

function xr(e, t, n) {
    return n < 0 && (n += 1), n > 1 && (n -= 1), n < 1 / 6 ? e + (t - e) * 6 * n : n < 1 / 2 ? t : n < 2 / 3 ? e + (t - e) * (2 / 3 - n) * 6 : e
}

function pf({
    hue: e,
    saturation: t,
    lightness: n,
    alpha: r
}) {
    e /= 360, t /= 100, n /= 100;
    let o = 0,
        s = 0,
        i = 0;
    if (!t) o = s = i = n;
    else {
        const a = n < .5 ? n * (1 + t) : n + t - n * t,
            c = 2 * n - a;
        o = xr(c, a, e + 1 / 3), s = xr(c, a, e), i = xr(c, a, e - 1 / 3)
    }
    return {
        red: Math.round(o * 255),
        green: Math.round(s * 255),
        blue: Math.round(i * 255),
        alpha: r
    }
}
const wr = (e, t, n) => {
        const r = e * e;
        return Math.sqrt(Math.max(0, n * (t * t - r) + r))
    },
    mf = [Ur, ut, At],
    gf = e => mf.find(t => t.test(e));

function as(e) {
    const t = gf(e);
    let n = t.parse(e);
    return t === At && (n = pf(n)), n
}
const ya = (e, t) => {
    const n = as(e),
        r = as(t),
        o = {
            ...n
        };
    return s => (o.red = wr(n.red, r.red, s), o.green = wr(n.green, r.green, s), o.blue = wr(n.blue, r.blue, s), o.alpha = J(n.alpha, r.alpha, s), ut.transform(o))
};

function vf(e) {
    var t, n;
    return isNaN(e) && an(e) && (((t = e.match(Qn)) === null || t === void 0 ? void 0 : t.length) || 0) + (((n = e.match(Hi)) === null || n === void 0 ? void 0 : n.length) || 0) > 0
}
const ba = {
        regex: au,
        countKey: "Vars",
        token: "${v}",
        parse: ee
    },
    xa = {
        regex: Hi,
        countKey: "Colors",
        token: "${c}",
        parse: ce.parse
    },
    wa = {
        regex: Qn,
        countKey: "Numbers",
        token: "${n}",
        parse: ht.parse
    };

function Cr(e, {
    regex: t,
    countKey: n,
    token: r,
    parse: o
}) {
    const s = e.tokenised.match(t);
    s && (e["num" + n] = s.length, e.tokenised = e.tokenised.replace(t, r), e.values.push(...s.map(o)))
}

function kn(e) {
    const t = e.toString(),
        n = {
            value: t,
            tokenised: t,
            values: [],
            numVars: 0,
            numColors: 0,
            numNumbers: 0
        };
    return n.value.includes("var(--") && Cr(n, ba), Cr(n, xa), Cr(n, wa), n
}

function Ca(e) {
    return kn(e).values
}

function Aa(e) {
    const {
        values: t,
        numColors: n,
        numVars: r,
        tokenised: o
    } = kn(e), s = t.length;
    return i => {
        let a = o;
        for (let c = 0; c < s; c++) c < r ? a = a.replace(ba.token, i[c]) : c < r + n ? a = a.replace(xa.token, ce.transform(i[c])) : a = a.replace(wa.token, Kt(i[c]));
        return a
    }
}
const yf = e => typeof e == "number" ? 0 : e;

function bf(e) {
    const t = Ca(e);
    return Aa(e)(t.map(yf))
}
const Je = {
        test: vf,
        parse: Ca,
        createTransformer: Aa,
        getAnimatableNone: bf
    },
    $a = (e, t) => n => `${n>0?t:e}`;

function Pa(e, t) {
    return typeof e == "number" ? n => J(e, t, n) : ce.test(e) ? ya(e, t) : e.startsWith("var(") ? $a(e, t) : Ea(e, t)
}
const Sa = (e, t) => {
        const n = [...e],
            r = n.length,
            o = e.map((s, i) => Pa(s, t[i]));
        return s => {
            for (let i = 0; i < r; i++) n[i] = o[i](s);
            return n
        }
    },
    xf = (e, t) => {
        const n = {
                ...e,
                ...t
            },
            r = {};
        for (const o in n) e[o] !== void 0 && t[o] !== void 0 && (r[o] = Pa(e[o], t[o]));
        return o => {
            for (const s in r) n[s] = r[s](o);
            return n
        }
    },
    Ea = (e, t) => {
        const n = Je.createTransformer(t),
            r = kn(e),
            o = kn(t);
        return r.numVars === o.numVars && r.numColors === o.numColors && r.numNumbers >= o.numNumbers ? Ze(Sa(r.values, o.values), n) : $a(e, t)
    },
    Qt = (e, t, n) => {
        const r = t - e;
        return r === 0 ? 1 : (n - e) / r
    },
    cs = (e, t) => n => J(e, t, n);

function wf(e) {
    return typeof e == "number" ? cs : typeof e == "string" ? ce.test(e) ? ya : Ea : Array.isArray(e) ? Sa : typeof e == "object" ? xf : cs
}

function Cf(e, t, n) {
    const r = [],
        o = n || wf(e[0]),
        s = e.length - 1;
    for (let i = 0; i < s; i++) {
        let a = o(e[i], e[i + 1]);
        if (t) {
            const c = Array.isArray(t) ? t[i] || ee : t;
            a = Ze(c, a)
        }
        r.push(a)
    }
    return r
}

function Ta(e, t, {
    clamp: n = !0,
    ease: r,
    mixer: o
} = {}) {
    const s = e.length;
    if (Ao(s === t.length), s === 1) return () => t[0];
    e[0] > e[s - 1] && (e = [...e].reverse(), t = [...t].reverse());
    const i = Cf(t, r, o),
        a = i.length,
        c = l => {
            let f = 0;
            if (a > 1)
                for (; f < e.length - 2 && !(l < e[f + 1]); f++);
            const d = Qt(e[f], e[f + 1], l);
            return i[f](d)
        };
    return n ? l => c(Qe(e[0], e[s - 1], l)) : c
}

function Af(e, t) {
    const n = e[e.length - 1];
    for (let r = 1; r <= t; r++) {
        const o = Qt(0, t, r);
        e.push(J(n, 1, o))
    }
}

function $f(e) {
    const t = [0];
    return Af(t, e.length - 1), t
}

function Pf(e, t) {
    return e.map(n => n * t)
}

function Sf(e, t) {
    return e.map(() => t || da).splice(0, e.length - 1)
}

function Ln({
    duration: e = 300,
    keyframes: t,
    times: n,
    ease: r = "easeInOut"
}) {
    const o = af(r) ? r.map(is) : is(r),
        s = {
            done: !1,
            value: t[0]
        },
        i = Pf(n && n.length === t.length ? n : $f(t), e),
        a = Ta(i, t, {
            ease: Array.isArray(o) ? o : Sf(t, o)
        });
    return {
        calculatedDuration: e,
        next: c => (s.value = a(c), s.done = c >= e, s)
    }
}

function Ma(e, t) {
    return t ? e * (1e3 / t) : 0
}
const Ef = 5;

function Da(e, t, n) {
    const r = Math.max(t - Ef, 0);
    return Ma(n - e(r), t - r)
}
const Ar = .001,
    Tf = .01,
    ls = 10,
    Mf = .05,
    Df = 1;

function Rf({
    duration: e = 800,
    bounce: t = .25,
    velocity: n = 0,
    mass: r = 1
}) {
    let o, s;
    qu(e <= qe(ls));
    let i = 1 - t;
    i = Qe(Mf, Df, i), e = Qe(Tf, ls, Fe(e)), i < 1 ? (o = l => {
        const f = l * i,
            d = f * e,
            h = f - n,
            p = Wr(l, i),
            g = Math.exp(-d);
        return Ar - h / p * g
    }, s = l => {
        const d = l * i * e,
            h = d * n + n,
            p = Math.pow(i, 2) * Math.pow(l, 2) * e,
            g = Math.exp(-d),
            m = Wr(Math.pow(l, 2), i);
        return (-o(l) + Ar > 0 ? -1 : 1) * ((h - p) * g) / m
    }) : (o = l => {
        const f = Math.exp(-l * e),
            d = (l - n) * e + 1;
        return -Ar + f * d
    }, s = l => {
        const f = Math.exp(-l * e),
            d = (n - l) * (e * e);
        return f * d
    });
    const a = 5 / e,
        c = _f(o, s, a);
    if (e = qe(e), isNaN(c)) return {
        stiffness: 100,
        damping: 10,
        duration: e
    };
    {
        const l = Math.pow(c, 2) * r;
        return {
            stiffness: l,
            damping: i * 2 * Math.sqrt(r * l),
            duration: e
        }
    }
}
const Of = 12;

function _f(e, t, n) {
    let r = n;
    for (let o = 1; o < Of; o++) r = r - e(r) / t(r);
    return r
}

function Wr(e, t) {
    return e * Math.sqrt(1 - t * t)
}
const kf = ["duration", "bounce"],
    Lf = ["stiffness", "damping", "mass"];

function us(e, t) {
    return t.some(n => e[n] !== void 0)
}

function If(e) {
    let t = {
        velocity: 0,
        stiffness: 100,
        damping: 10,
        mass: 1,
        isResolvedFromDuration: !1,
        ...e
    };
    if (!us(e, Lf) && us(e, kf)) {
        const n = Rf(e);
        t = {
            ...t,
            ...n,
            velocity: 0,
            mass: 1
        }, t.isResolvedFromDuration = !0
    }
    return t
}

function Ra({
    keyframes: e,
    restDelta: t,
    restSpeed: n,
    ...r
}) {
    const o = e[0],
        s = e[e.length - 1],
        i = {
            done: !1,
            value: o
        },
        {
            stiffness: a,
            damping: c,
            mass: l,
            velocity: f,
            duration: d,
            isResolvedFromDuration: h
        } = If(r),
        p = f ? -Fe(f) : 0,
        g = c / (2 * Math.sqrt(a * l)),
        m = s - o,
        b = Fe(Math.sqrt(a / l)),
        x = Math.abs(m) < 5;
    n || (n = x ? .01 : 2), t || (t = x ? .005 : .5);
    let v;
    if (g < 1) {
        const y = Wr(b, g);
        v = C => {
            const $ = Math.exp(-g * b * C);
            return s - $ * ((p + g * b * m) / y * Math.sin(y * C) + m * Math.cos(y * C))
        }
    } else if (g === 1) v = y => s - Math.exp(-b * y) * (m + (p + b * m) * y);
    else {
        const y = b * Math.sqrt(g * g - 1);
        v = C => {
            const $ = Math.exp(-g * b * C),
                A = Math.min(y * C, 300);
            return s - $ * ((p + g * b * m) * Math.sinh(A) + y * m * Math.cosh(A)) / y
        }
    }
    return {
        calculatedDuration: h && d || null,
        next: y => {
            const C = v(y);
            if (h) i.done = y >= d;
            else {
                let $ = p;
                y !== 0 && (g < 1 ? $ = Da(v, y, C) : $ = 0);
                const A = Math.abs($) <= n,
                    w = Math.abs(s - C) <= t;
                i.done = A && w
            }
            return i.value = i.done ? s : C, i
        }
    }
}

function fs({
    keyframes: e,
    velocity: t = 0,
    power: n = .8,
    timeConstant: r = 325,
    bounceDamping: o = 10,
    bounceStiffness: s = 500,
    modifyTarget: i,
    min: a,
    max: c,
    restDelta: l = .5,
    restSpeed: f
}) {
    const d = e[0],
        h = {
            done: !1,
            value: d
        },
        p = S => a !== void 0 && S < a || c !== void 0 && S > c,
        g = S => a === void 0 ? c : c === void 0 || Math.abs(a - S) < Math.abs(c - S) ? a : c;
    let m = n * t;
    const b = d + m,
        x = i === void 0 ? b : i(b);
    x !== b && (m = x - d);
    const v = S => -m * Math.exp(-S / r),
        y = S => x + v(S),
        C = S => {
            const E = v(S),
                j = y(S);
            h.done = Math.abs(E) <= l, h.value = h.done ? x : j
        };
    let $, A;
    const w = S => {
        p(h.value) && ($ = S, A = Ra({
            keyframes: [h.value, g(h.value)],
            velocity: Da(y, S, h.value),
            damping: o,
            stiffness: s,
            restDelta: l,
            restSpeed: f
        }))
    };
    return w(0), {
        calculatedDuration: null,
        next: S => {
            let E = !1;
            return !A && $ === void 0 && (E = !0, C(S), w(S)), $ !== void 0 && S > $ ? A.next(S - $) : (!E && C(S), h)
        }
    }
}
const Vf = e => {
        const t = ({
            timestamp: n
        }) => e(n);
        return {
            start: () => Q.update(t, !0),
            stop: () => je(t),
            now: () => ie.isProcessing ? ie.timestamp : performance.now()
        }
    },
    ds = 2e4;

function hs(e) {
    let t = 0;
    const n = 50;
    let r = e.next(t);
    for (; !r.done && t < ds;) t += n, r = e.next(t);
    return t >= ds ? 1 / 0 : t
}
const Nf = {
    decay: fs,
    inertia: fs,
    tween: Ln,
    keyframes: Ln,
    spring: Ra
};

function In({
    autoplay: e = !0,
    delay: t = 0,
    driver: n = Vf,
    keyframes: r,
    type: o = "keyframes",
    repeat: s = 0,
    repeatDelay: i = 0,
    repeatType: a = "loop",
    onPlay: c,
    onStop: l,
    onComplete: f,
    onUpdate: d,
    ...h
}) {
    let p = 1,
        g = !1,
        m, b;
    const x = () => {
        b = new Promise(W => {
            m = W
        })
    };
    x();
    let v;
    const y = Nf[o] || Ln;
    let C;
    y !== Ln && typeof r[0] != "number" && (C = Ta([0, 100], r, {
        clamp: !1
    }), r = [0, 100]);
    const $ = y({
        ...h,
        keyframes: r
    });
    let A;
    a === "mirror" && (A = y({
        ...h,
        keyframes: [...r].reverse(),
        velocity: -(h.velocity || 0)
    }));
    let w = "idle",
        S = null,
        E = null,
        j = null;
    $.calculatedDuration === null && s && ($.calculatedDuration = hs($));
    const {
        calculatedDuration: I
    } = $;
    let G = 1 / 0,
        _ = 1 / 0;
    I !== null && (G = I + i, _ = G * (s + 1) - i);
    let L = 0;
    const k = W => {
            if (E === null) return;
            p > 0 && (E = Math.min(E, W)), p < 0 && (E = Math.min(W - _ / p, E)), S !== null ? L = S : L = Math.round(W - E) * p;
            const P = L - t * (p >= 0 ? 1 : -1),
                T = p >= 0 ? P < 0 : P > _;
            L = Math.max(P, 0), w === "finished" && S === null && (L = _);
            let R = L,
                D = $;
            if (s) {
                const z = L / G;
                let X = Math.floor(z),
                    ae = z % 1;
                !ae && z >= 1 && (ae = 1), ae === 1 && X--, X = Math.min(X, s + 1);
                const $e = !!(X % 2);
                $e && (a === "reverse" ? (ae = 1 - ae, i && (ae -= i / G)) : a === "mirror" && (D = A));
                let Pe = Qe(0, 1, ae);
                L > _ && (Pe = a === "reverse" && $e ? 1 : 0), R = Pe * G
            }
            const O = T ? {
                done: !1,
                value: r[0]
            } : D.next(R);
            C && (O.value = C(O.value));
            let {
                done: F
            } = O;
            !T && I !== null && (F = p >= 0 ? L >= _ : L <= 0);
            const H = S === null && (w === "finished" || w === "running" && F);
            return d && d(O.value), H && q(), O
        },
        U = () => {
            v && v.stop(), v = void 0
        },
        Z = () => {
            w = "idle", U(), m(), x(), E = j = null
        },
        q = () => {
            w = "finished", f && f(), U(), m()
        },
        N = () => {
            if (g) return;
            v || (v = n(k));
            const W = v.now();
            c && c(), S !== null ? E = W - S : (!E || w === "finished") && (E = W), w === "finished" && x(), j = E, S = null, w = "running", v.start()
        };
    e && N();
    const Y = {
        then(W, P) {
            return b.then(W, P)
        },
        get time() {
            return Fe(L)
        },
        set time(W) {
            W = qe(W), L = W, S !== null || !v || p === 0 ? S = W : E = v.now() - W / p
        },
        get duration() {
            const W = $.calculatedDuration === null ? hs($) : $.calculatedDuration;
            return Fe(W)
        },
        get speed() {
            return p
        },
        set speed(W) {
            W === p || !v || (p = W, Y.time = Fe(L))
        },
        get state() {
            return w
        },
        play: N,
        pause: () => {
            w = "paused", S = L
        },
        stop: () => {
            g = !0, w !== "idle" && (w = "idle", l && l(), Z())
        },
        cancel: () => {
            j !== null && k(j), Z()
        },
        complete: () => {
            w = "finished"
        },
        sample: W => (E = 0, k(W))
    };
    return Y
}

function Ff(e) {
    let t;
    return () => (t === void 0 && (t = e()), t)
}
const jf = Ff(() => Object.hasOwnProperty.call(Element.prototype, "animate")),
    Bf = new Set(["opacity", "clipPath", "filter", "transform", "backgroundColor"]),
    mn = 10,
    Uf = 2e4,
    Wf = (e, t) => t.type === "spring" || e === "backgroundColor" || !ca(t.ease);

function Hf(e, t, {
    onUpdate: n,
    onComplete: r,
    ...o
}) {
    if (!(jf() && Bf.has(t) && !o.repeatDelay && o.repeatType !== "mirror" && o.damping !== 0 && o.type !== "inertia")) return !1;
    let i = !1,
        a, c;
    const l = () => {
        c = new Promise(v => {
            a = v
        })
    };
    l();
    let {
        keyframes: f,
        duration: d = 300,
        ease: h,
        times: p
    } = o;
    if (Wf(t, o)) {
        const v = In({
            ...o,
            repeat: 0,
            delay: 0
        });
        let y = {
            done: !1,
            value: f[0]
        };
        const C = [];
        let $ = 0;
        for (; !y.done && $ < Uf;) y = v.sample($), C.push(y.value), $ += mn;
        p = void 0, f = C, d = $ - mn, h = "linear"
    }
    const g = Ju(e.owner.current, t, f, {
            ...o,
            duration: d,
            ease: h,
            times: p
        }),
        m = () => g.cancel(),
        b = () => {
            Q.update(m), a(), l()
        };
    return g.onfinish = () => {
        e.set(ef(f, o)), r && r(), b()
    }, {
        then(v, y) {
            return c.then(v, y)
        },
        attachTimeline(v) {
            return g.timeline = v, g.onfinish = null, ee
        },
        get time() {
            return Fe(g.currentTime || 0)
        },
        set time(v) {
            g.currentTime = qe(v)
        },
        get speed() {
            return g.playbackRate
        },
        set speed(v) {
            g.playbackRate = v
        },
        get duration() {
            return Fe(d)
        },
        play: () => {
            i || (g.play(), je(m))
        },
        pause: () => g.pause(),
        stop: () => {
            if (i = !0, g.playState === "idle") return;
            const {
                currentTime: v
            } = g;
            if (v) {
                const y = In({
                    ...o,
                    autoplay: !1
                });
                e.setWithVelocity(y.sample(v - mn).value, y.sample(v).value, mn)
            }
            b()
        },
        complete: () => g.finish(),
        cancel: b
    }
}

function Gf({
    keyframes: e,
    delay: t,
    onUpdate: n,
    onComplete: r
}) {
    const o = () => (n && n(e[e.length - 1]), r && r(), {
        time: 0,
        speed: 1,
        duration: 0,
        play: ee,
        pause: ee,
        stop: ee,
        then: s => (s(), Promise.resolve()),
        cancel: ee,
        complete: ee
    });
    return t ? In({
        keyframes: [0, 1],
        duration: 0,
        delay: t,
        onComplete: o
    }) : o()
}
const Kf = {
        type: "spring",
        stiffness: 500,
        damping: 25,
        restSpeed: 10
    },
    zf = e => ({
        type: "spring",
        stiffness: 550,
        damping: e === 0 ? 2 * Math.sqrt(550) : 30,
        restSpeed: 10
    }),
    Yf = {
        type: "keyframes",
        duration: .8
    },
    Xf = {
        type: "keyframes",
        ease: [.25, .1, .35, 1],
        duration: .3
    },
    Zf = (e, {
        keyframes: t
    }) => t.length > 2 ? Yf : dt.has(e) ? e.startsWith("scale") ? zf(t[1]) : Kf : Xf,
    Hr = (e, t) => e === "zIndex" ? !1 : !!(typeof t == "number" || Array.isArray(t) || typeof t == "string" && (Je.test(t) || t === "0") && !t.startsWith("url(")),
    qf = new Set(["brightness", "contrast", "saturate", "opacity"]);

function Qf(e) {
    const [t, n] = e.slice(0, -1).split("(");
    if (t === "drop-shadow") return e;
    const [r] = n.match(Qn) || [];
    if (!r) return e;
    const o = n.replace(r, "");
    let s = qf.has(t) ? 1 : 0;
    return r !== n && (s *= 100), t + "(" + s + o + ")"
}
const Jf = /([a-z-]*)\(.*?\)/g,
    Gr = {
        ...Je,
        getAnimatableNone: e => {
            const t = e.match(Jf);
            return t ? t.map(Qf).join(" ") : e
        }
    },
    ed = {
        ...Gi,
        color: ce,
        backgroundColor: ce,
        outlineColor: ce,
        fill: ce,
        stroke: ce,
        borderColor: ce,
        borderTopColor: ce,
        borderRightColor: ce,
        borderBottomColor: ce,
        borderLeftColor: ce,
        filter: Gr,
        WebkitFilter: Gr
    },
    Eo = e => ed[e];

function Oa(e, t) {
    let n = Eo(e);
    return n !== Gr && (n = Je), n.getAnimatableNone ? n.getAnimatableNone(t) : void 0
}
const _a = e => /^0[^.\s]+$/.test(e);

function td(e) {
    if (typeof e == "number") return e === 0;
    if (e !== null) return e === "none" || e === "0" || _a(e)
}

function nd(e, t, n, r) {
    const o = Hr(t, n);
    let s;
    Array.isArray(n) ? s = [...n] : s = [null, n];
    const i = r.from !== void 0 ? r.from : e.get();
    let a;
    const c = [];
    for (let l = 0; l < s.length; l++) s[l] === null && (s[l] = l === 0 ? i : s[l - 1]), td(s[l]) && c.push(l), typeof s[l] == "string" && s[l] !== "none" && s[l] !== "0" && (a = s[l]);
    if (o && c.length && a)
        for (let l = 0; l < c.length; l++) {
            const f = c[l];
            s[f] = Oa(t, a)
        }
    return s
}

function rd({
    when: e,
    delay: t,
    delayChildren: n,
    staggerChildren: r,
    staggerDirection: o,
    repeat: s,
    repeatType: i,
    repeatDelay: a,
    from: c,
    elapsed: l,
    ...f
}) {
    return !!Object.keys(f).length
}

function ka(e, t) {
    return e[t] || e.default || e
}
const To = (e, t, n, r = {}) => o => {
    const s = ka(r, e) || {},
        i = s.delay || r.delay || 0;
    let {
        elapsed: a = 0
    } = r;
    a = a - qe(i);
    const c = nd(t, e, n, s),
        l = c[0],
        f = c[c.length - 1],
        d = Hr(e, l),
        h = Hr(e, f);
    let p = {
        keyframes: c,
        velocity: t.getVelocity(),
        ease: "easeOut",
        ...s,
        delay: -a,
        onUpdate: g => {
            t.set(g), s.onUpdate && s.onUpdate(g)
        },
        onComplete: () => {
            o(), s.onComplete && s.onComplete()
        }
    };
    if (rd(s) || (p = {
            ...p,
            ...Zf(e, p)
        }), p.duration && (p.duration = qe(p.duration)), p.repeatDelay && (p.repeatDelay = qe(p.repeatDelay)), !d || !h || Qu.current || s.type === !1) return Gf(p);
    if (t.owner && t.owner.current instanceof HTMLElement && !t.owner.getProps().onUpdate) {
        const g = Hf(t, e, p);
        if (g) return g
    }
    return In(p)
};

function Vn(e) {
    return !!(fe(e) && e.add)
}
const od = e => /^\-?\d*\.?\d+$/.test(e);

function Mo(e, t) {
    e.indexOf(t) === -1 && e.push(t)
}

function Do(e, t) {
    const n = e.indexOf(t);
    n > -1 && e.splice(n, 1)
}
class Ro {
    constructor() {
        this.subscriptions = []
    }
    add(t) {
        return Mo(this.subscriptions, t), () => Do(this.subscriptions, t)
    }
    notify(t, n, r) {
        const o = this.subscriptions.length;
        if (o)
            if (o === 1) this.subscriptions[0](t, n, r);
            else
                for (let s = 0; s < o; s++) {
                    const i = this.subscriptions[s];
                    i && i(t, n, r)
                }
    }
    getSize() {
        return this.subscriptions.length
    }
    clear() {
        this.subscriptions.length = 0
    }
}
const sd = e => !isNaN(parseFloat(e));
class id {
    constructor(t, n = {}) {
        this.version = "10.15.1", this.timeDelta = 0, this.lastUpdated = 0, this.canTrackVelocity = !1, this.events = {}, this.updateAndNotify = (r, o = !0) => {
            this.prev = this.current, this.current = r;
            const {
                delta: s,
                timestamp: i
            } = ie;
            this.lastUpdated !== i && (this.timeDelta = s, this.lastUpdated = i, Q.postRender(this.scheduleVelocityCheck)), this.prev !== this.current && this.events.change && this.events.change.notify(this.current), this.events.velocityChange && this.events.velocityChange.notify(this.getVelocity()), o && this.events.renderRequest && this.events.renderRequest.notify(this.current)
        }, this.scheduleVelocityCheck = () => Q.postRender(this.velocityCheck), this.velocityCheck = ({
            timestamp: r
        }) => {
            r !== this.lastUpdated && (this.prev = this.current, this.events.velocityChange && this.events.velocityChange.notify(this.getVelocity()))
        }, this.hasAnimated = !1, this.prev = this.current = t, this.canTrackVelocity = sd(this.current), this.owner = n.owner
    }
    onChange(t) {
        return this.on("change", t)
    }
    on(t, n) {
        this.events[t] || (this.events[t] = new Ro);
        const r = this.events[t].add(n);
        return t === "change" ? () => {
            r(), Q.read(() => {
                this.events.change.getSize() || this.stop()
            })
        } : r
    }
    clearListeners() {
        for (const t in this.events) this.events[t].clear()
    }
    attach(t, n) {
        this.passiveEffect = t, this.stopPassiveEffect = n
    }
    set(t, n = !0) {
        !n || !this.passiveEffect ? this.updateAndNotify(t, n) : this.passiveEffect(t, this.updateAndNotify)
    }
    setWithVelocity(t, n, r) {
        this.set(n), this.prev = t, this.timeDelta = r
    }
    jump(t) {
        this.updateAndNotify(t), this.prev = t, this.stop(), this.stopPassiveEffect && this.stopPassiveEffect()
    }
    get() {
        return this.current
    }
    getPrevious() {
        return this.prev
    }
    getVelocity() {
        return this.canTrackVelocity ? Ma(parseFloat(this.current) - parseFloat(this.prev), this.timeDelta) : 0
    }
    start(t) {
        return this.stop(), new Promise(n => {
            this.hasAnimated = !0, this.animation = t(n), this.events.animationStart && this.events.animationStart.notify()
        }).then(() => {
            this.events.animationComplete && this.events.animationComplete.notify(), this.clearAnimation()
        })
    }
    stop() {
        this.animation && (this.animation.stop(), this.events.animationCancel && this.events.animationCancel.notify()), this.clearAnimation()
    }
    isAnimating() {
        return !!this.animation
    }
    clearAnimation() {
        delete this.animation
    }
    destroy() {
        this.clearListeners(), this.stop(), this.stopPassiveEffect && this.stopPassiveEffect()
    }
}

function Mt(e, t) {
    return new id(e, t)
}
const La = e => t => t.test(e),
    ad = {
        test: e => e === "auto",
        parse: e => e
    },
    Ia = [ht, B, Oe, ze, fu, uu, ad],
    jt = e => Ia.find(La(e)),
    cd = [...Ia, ce, Je],
    ld = e => cd.find(La(e));

function ud(e, t, n) {
    e.hasValue(t) ? e.getValue(t).set(n) : e.addValue(t, Mt(n))
}

function fd(e, t) {
    const n = er(e, t);
    let {
        transitionEnd: r = {},
        transition: o = {},
        ...s
    } = n ? e.makeTargetAnimatable(n, !1) : {};
    s = {
        ...s,
        ...r
    };
    for (const i in s) {
        const a = Pu(s[i]);
        ud(e, i, a)
    }
}

function dd(e, t, n) {
    var r, o;
    const s = Object.keys(t).filter(a => !e.hasValue(a)),
        i = s.length;
    if (i)
        for (let a = 0; a < i; a++) {
            const c = s[a],
                l = t[c];
            let f = null;
            Array.isArray(l) && (f = l[0]), f === null && (f = (o = (r = n[c]) !== null && r !== void 0 ? r : e.readValue(c)) !== null && o !== void 0 ? o : t[c]), f != null && (typeof f == "string" && (od(f) || _a(f)) ? f = parseFloat(f) : !ld(f) && Je.test(l) && (f = Oa(c, l)), e.addValue(c, Mt(f, {
                owner: e
            })), n[c] === void 0 && (n[c] = f), f !== null && e.setBaseTarget(c, f))
        }
}

function hd(e, t) {
    return t ? (t[e] || t.default || t).from : void 0
}

function pd(e, t, n) {
    const r = {};
    for (const o in e) {
        const s = hd(o, t);
        if (s !== void 0) r[o] = s;
        else {
            const i = n.getValue(o);
            i && (r[o] = i.get())
        }
    }
    return r
}

function md({
    protectedKeys: e,
    needsAnimating: t
}, n) {
    const r = e.hasOwnProperty(n) && t[n] !== !0;
    return t[n] = !1, r
}

function Va(e, t, {
    delay: n = 0,
    transitionOverride: r,
    type: o
} = {}) {
    let {
        transition: s = e.getDefaultTransition(),
        transitionEnd: i,
        ...a
    } = e.makeTargetAnimatable(t);
    const c = e.getValue("willChange");
    r && (s = r);
    const l = [],
        f = o && e.animationState && e.animationState.getState()[o];
    for (const d in a) {
        const h = e.getValue(d),
            p = a[d];
        if (!h || p === void 0 || f && md(f, d)) continue;
        const g = {
            delay: n,
            elapsed: 0,
            ...s
        };
        if (window.HandoffAppearAnimations && !h.hasAnimated) {
            const b = e.getProps()[Zu];
            b && (g.elapsed = window.HandoffAppearAnimations(b, d, h, Q))
        }
        h.start(To(d, h, p, e.shouldReduceMotion && dt.has(d) ? {
            type: !1
        } : g));
        const m = h.animation;
        Vn(c) && (c.add(d), m.then(() => c.remove(d))), l.push(m)
    }
    return i && Promise.all(l).then(() => {
        i && fd(e, i)
    }), l
}

function Kr(e, t, n = {}) {
    const r = er(e, t, n.custom);
    let {
        transition: o = e.getDefaultTransition() || {}
    } = r || {};
    n.transitionOverride && (o = n.transitionOverride);
    const s = r ? () => Promise.all(Va(e, r, n)) : () => Promise.resolve(),
        i = e.variantChildren && e.variantChildren.size ? (c = 0) => {
            const {
                delayChildren: l = 0,
                staggerChildren: f,
                staggerDirection: d
            } = o;
            return gd(e, t, l + c, f, d, n)
        } : () => Promise.resolve(),
        {
            when: a
        } = o;
    if (a) {
        const [c, l] = a === "beforeChildren" ? [s, i] : [i, s];
        return c().then(() => l())
    } else return Promise.all([s(), i(n.delay)])
}

function gd(e, t, n = 0, r = 0, o = 1, s) {
    const i = [],
        a = (e.variantChildren.size - 1) * r,
        c = o === 1 ? (l = 0) => l * r : (l = 0) => a - l * r;
    return Array.from(e.variantChildren).sort(vd).forEach((l, f) => {
        l.notify("AnimationStart", t), i.push(Kr(l, t, {
            ...s,
            delay: n + c(f)
        }).then(() => l.notify("AnimationComplete", t)))
    }), Promise.all(i)
}

function vd(e, t) {
    return e.sortNodePosition(t)
}

function yd(e, t, n = {}) {
    e.notify("AnimationStart", t);
    let r;
    if (Array.isArray(t)) {
        const o = t.map(s => Kr(e, s, n));
        r = Promise.all(o)
    } else if (typeof t == "string") r = Kr(e, t, n);
    else {
        const o = typeof t == "function" ? er(e, t, n.custom) : t;
        r = Promise.all(Va(e, o, n))
    }
    return r.then(() => e.notify("AnimationComplete", t))
}
const bd = [...fo].reverse(),
    xd = fo.length;

function wd(e) {
    return t => Promise.all(t.map(({
        animation: n,
        options: r
    }) => yd(e, n, r)))
}

function Cd(e) {
    let t = wd(e);
    const n = $d();
    let r = !0;
    const o = (c, l) => {
        const f = er(e, l);
        if (f) {
            const {
                transition: d,
                transitionEnd: h,
                ...p
            } = f;
            c = {
                ...c,
                ...p,
                ...h
            }
        }
        return c
    };

    function s(c) {
        t = c(e)
    }

    function i(c, l) {
        const f = e.getProps(),
            d = e.getVariantContext(!0) || {},
            h = [],
            p = new Set;
        let g = {},
            m = 1 / 0;
        for (let x = 0; x < xd; x++) {
            const v = bd[x],
                y = n[v],
                C = f[v] !== void 0 ? f[v] : d[v],
                $ = Zt(C),
                A = v === l ? y.isActive : null;
            A === !1 && (m = x);
            let w = C === d[v] && C !== f[v] && $;
            if (w && r && e.manuallyAnimateOnMount && (w = !1), y.protectedKeys = {
                    ...g
                }, !y.isActive && A === null || !C && !y.prevProp || Zn(C) || typeof C == "boolean") continue;
            const S = Ad(y.prevProp, C);
            let E = S || v === l && y.isActive && !w && $ || x > m && $;
            const j = Array.isArray(C) ? C : [C];
            let I = j.reduce(o, {});
            A === !1 && (I = {});
            const {
                prevResolvedValues: G = {}
            } = y, _ = {
                ...G,
                ...I
            }, L = k => {
                E = !0, p.delete(k), y.needsAnimating[k] = !0
            };
            for (const k in _) {
                const U = I[k],
                    Z = G[k];
                g.hasOwnProperty(k) || (U !== Z ? _n(U) && _n(Z) ? !ia(U, Z) || S ? L(k) : y.protectedKeys[k] = !0 : U !== void 0 ? L(k) : p.add(k) : U !== void 0 && p.has(k) ? L(k) : y.protectedKeys[k] = !0)
            }
            y.prevProp = C, y.prevResolvedValues = I, y.isActive && (g = {
                ...g,
                ...I
            }), r && e.blockInitialAnimation && (E = !1), E && !w && h.push(...j.map(k => ({
                animation: k,
                options: {
                    type: v,
                    ...c
                }
            })))
        }
        if (p.size) {
            const x = {};
            p.forEach(v => {
                const y = e.getBaseTarget(v);
                y !== void 0 && (x[v] = y)
            }), h.push({
                animation: x
            })
        }
        let b = !!h.length;
        return r && f.initial === !1 && !e.manuallyAnimateOnMount && (b = !1), r = !1, b ? t(h) : Promise.resolve()
    }

    function a(c, l, f) {
        var d;
        if (n[c].isActive === l) return Promise.resolve();
        (d = e.variantChildren) === null || d === void 0 || d.forEach(p => {
            var g;
            return (g = p.animationState) === null || g === void 0 ? void 0 : g.setActive(c, l)
        }), n[c].isActive = l;
        const h = i(f, c);
        for (const p in n) n[p].protectedKeys = {};
        return h
    }
    return {
        animateChanges: i,
        setActive: a,
        setAnimateFunction: s,
        getState: () => n
    }
}

function Ad(e, t) {
    return typeof t == "string" ? t !== e : Array.isArray(t) ? !ia(t, e) : !1
}

function at(e = !1) {
    return {
        isActive: e,
        protectedKeys: {},
        needsAnimating: {},
        prevResolvedValues: {}
    }
}

function $d() {
    return {
        animate: at(!0),
        whileInView: at(),
        whileHover: at(),
        whileTap: at(),
        whileDrag: at(),
        whileFocus: at(),
        exit: at()
    }
}
class Pd extends rt {
    constructor(t) {
        super(t), t.animationState || (t.animationState = Cd(t))
    }
    updateAnimationControlsSubscription() {
        const {
            animate: t
        } = this.node.getProps();
        this.unmount(), Zn(t) && (this.unmount = t.subscribe(this.node))
    }
    mount() {
        this.updateAnimationControlsSubscription()
    }
    update() {
        const {
            animate: t
        } = this.node.getProps(), {
            animate: n
        } = this.node.prevProps || {};
        t !== n && this.updateAnimationControlsSubscription()
    }
    unmount() {}
}
let Sd = 0;
class Ed extends rt {
    constructor() {
        super(...arguments), this.id = Sd++
    }
    update() {
        if (!this.node.presenceContext) return;
        const {
            isPresent: t,
            onExitComplete: n,
            custom: r
        } = this.node.presenceContext, {
            isPresent: o
        } = this.node.prevPresenceContext || {};
        if (!this.node.animationState || t === o) return;
        const s = this.node.animationState.setActive("exit", !t, {
            custom: r ?? this.node.getProps().custom
        });
        n && !t && s.then(() => n(this.id))
    }
    mount() {
        const {
            register: t
        } = this.node.presenceContext || {};
        t && (this.unmount = t(this.id))
    }
    unmount() {}
}
const Td = {
        animation: {
            Feature: Pd
        },
        exit: {
            Feature: Ed
        }
    },
    ps = (e, t) => Math.abs(e - t);

function Md(e, t) {
    const n = ps(e.x, t.x),
        r = ps(e.y, t.y);
    return Math.sqrt(n ** 2 + r ** 2)
}
class Na {
    constructor(t, n, {
        transformPagePoint: r
    } = {}) {
        if (this.startEvent = null, this.lastMoveEvent = null, this.lastMoveEventInfo = null, this.handlers = {}, this.updatePoint = () => {
                if (!(this.lastMoveEvent && this.lastMoveEventInfo)) return;
                const l = Pr(this.lastMoveEventInfo, this.history),
                    f = this.startEvent !== null,
                    d = Md(l.offset, {
                        x: 0,
                        y: 0
                    }) >= 3;
                if (!f && !d) return;
                const {
                    point: h
                } = l, {
                    timestamp: p
                } = ie;
                this.history.push({
                    ...h,
                    timestamp: p
                });
                const {
                    onStart: g,
                    onMove: m
                } = this.handlers;
                f || (g && g(this.lastMoveEvent, l), this.startEvent = this.lastMoveEvent), m && m(this.lastMoveEvent, l)
            }, this.handlePointerMove = (l, f) => {
                this.lastMoveEvent = l, this.lastMoveEventInfo = $r(f, this.transformPagePoint), Q.update(this.updatePoint, !0)
            }, this.handlePointerUp = (l, f) => {
                if (this.end(), !(this.lastMoveEvent && this.lastMoveEventInfo)) return;
                const {
                    onEnd: d,
                    onSessionEnd: h
                } = this.handlers, p = Pr(l.type === "pointercancel" ? this.lastMoveEventInfo : $r(f, this.transformPagePoint), this.history);
                this.startEvent && d && d(l, p), h && h(l, p)
            }, !ta(t)) return;
        this.handlers = n, this.transformPagePoint = r;
        const o = Jn(t),
            s = $r(o, this.transformPagePoint),
            {
                point: i
            } = s,
            {
                timestamp: a
            } = ie;
        this.history = [{
            ...i,
            timestamp: a
        }];
        const {
            onSessionStart: c
        } = n;
        c && c(t, Pr(s, this.history)), this.removeListeners = Ze(Ne(window, "pointermove", this.handlePointerMove), Ne(window, "pointerup", this.handlePointerUp), Ne(window, "pointercancel", this.handlePointerUp))
    }
    updateHandlers(t) {
        this.handlers = t
    }
    end() {
        this.removeListeners && this.removeListeners(), je(this.updatePoint)
    }
}

function $r(e, t) {
    return t ? {
        point: t(e.point)
    } : e
}

function ms(e, t) {
    return {
        x: e.x - t.x,
        y: e.y - t.y
    }
}

function Pr({
    point: e
}, t) {
    return {
        point: e,
        delta: ms(e, Fa(t)),
        offset: ms(e, Dd(t)),
        velocity: Rd(t, .1)
    }
}

function Dd(e) {
    return e[0]
}

function Fa(e) {
    return e[e.length - 1]
}

function Rd(e, t) {
    if (e.length < 2) return {
        x: 0,
        y: 0
    };
    let n = e.length - 1,
        r = null;
    const o = Fa(e);
    for (; n >= 0 && (r = e[n], !(o.timestamp - r.timestamp > qe(t)));) n--;
    if (!r) return {
        x: 0,
        y: 0
    };
    const s = Fe(o.timestamp - r.timestamp);
    if (s === 0) return {
        x: 0,
        y: 0
    };
    const i = {
        x: (o.x - r.x) / s,
        y: (o.y - r.y) / s
    };
    return i.x === 1 / 0 && (i.x = 0), i.y === 1 / 0 && (i.y = 0), i
}

function be(e) {
    return e.max - e.min
}

function zr(e, t = 0, n = .01) {
    return Math.abs(e - t) <= n
}

function gs(e, t, n, r = .5) {
    e.origin = r, e.originPoint = J(t.min, t.max, e.origin), e.scale = be(n) / be(t), (zr(e.scale, 1, 1e-4) || isNaN(e.scale)) && (e.scale = 1), e.translate = J(n.min, n.max, e.origin) - e.originPoint, (zr(e.translate) || isNaN(e.translate)) && (e.translate = 0)
}

function zt(e, t, n, r) {
    gs(e.x, t.x, n.x, r ? r.originX : void 0), gs(e.y, t.y, n.y, r ? r.originY : void 0)
}

function vs(e, t, n) {
    e.min = n.min + t.min, e.max = e.min + be(t)
}

function Od(e, t, n) {
    vs(e.x, t.x, n.x), vs(e.y, t.y, n.y)
}

function ys(e, t, n) {
    e.min = t.min - n.min, e.max = e.min + be(t)
}

function Yt(e, t, n) {
    ys(e.x, t.x, n.x), ys(e.y, t.y, n.y)
}

function _d(e, {
    min: t,
    max: n
}, r) {
    return t !== void 0 && e < t ? e = r ? J(t, e, r.min) : Math.max(e, t) : n !== void 0 && e > n && (e = r ? J(n, e, r.max) : Math.min(e, n)), e
}

function bs(e, t, n) {
    return {
        min: t !== void 0 ? e.min + t : void 0,
        max: n !== void 0 ? e.max + n - (e.max - e.min) : void 0
    }
}

function kd(e, {
    top: t,
    left: n,
    bottom: r,
    right: o
}) {
    return {
        x: bs(e.x, n, o),
        y: bs(e.y, t, r)
    }
}

function xs(e, t) {
    let n = t.min - e.min,
        r = t.max - e.max;
    return t.max - t.min < e.max - e.min && ([n, r] = [r, n]), {
        min: n,
        max: r
    }
}

function Ld(e, t) {
    return {
        x: xs(e.x, t.x),
        y: xs(e.y, t.y)
    }
}

function Id(e, t) {
    let n = .5;
    const r = be(e),
        o = be(t);
    return o > r ? n = Qt(t.min, t.max - r, e.min) : r > o && (n = Qt(e.min, e.max - o, t.min)), Qe(0, 1, n)
}

function Vd(e, t) {
    const n = {};
    return t.min !== void 0 && (n.min = t.min - e.min), t.max !== void 0 && (n.max = t.max - e.min), n
}
const Yr = .35;

function Nd(e = Yr) {
    return e === !1 ? e = 0 : e === !0 && (e = Yr), {
        x: ws(e, "left", "right"),
        y: ws(e, "top", "bottom")
    }
}

function ws(e, t, n) {
    return {
        min: Cs(e, t),
        max: Cs(e, n)
    }
}

function Cs(e, t) {
    return typeof e == "number" ? e : e[t] || 0
}
const As = () => ({
        translate: 0,
        scale: 1,
        origin: 0,
        originPoint: 0
    }),
    $t = () => ({
        x: As(),
        y: As()
    }),
    $s = () => ({
        min: 0,
        max: 0
    }),
    ne = () => ({
        x: $s(),
        y: $s()
    });

function De(e) {
    return [e("x"), e("y")]
}

function ja({
    top: e,
    left: t,
    right: n,
    bottom: r
}) {
    return {
        x: {
            min: t,
            max: n
        },
        y: {
            min: e,
            max: r
        }
    }
}

function Fd({
    x: e,
    y: t
}) {
    return {
        top: t.min,
        right: e.max,
        bottom: t.max,
        left: e.min
    }
}

function jd(e, t) {
    if (!t) return e;
    const n = t({
            x: e.left,
            y: e.top
        }),
        r = t({
            x: e.right,
            y: e.bottom
        });
    return {
        top: n.y,
        left: n.x,
        bottom: r.y,
        right: r.x
    }
}

function Sr(e) {
    return e === void 0 || e === 1
}

function Xr({
    scale: e,
    scaleX: t,
    scaleY: n
}) {
    return !Sr(e) || !Sr(t) || !Sr(n)
}

function ct(e) {
    return Xr(e) || Ba(e) || e.z || e.rotate || e.rotateX || e.rotateY
}

function Ba(e) {
    return Ps(e.x) || Ps(e.y)
}

function Ps(e) {
    return e && e !== "0%"
}

function Nn(e, t, n) {
    const r = e - n,
        o = t * r;
    return n + o
}

function Ss(e, t, n, r, o) {
    return o !== void 0 && (e = Nn(e, o, r)), Nn(e, n, r) + t
}

function Zr(e, t = 0, n = 1, r, o) {
    e.min = Ss(e.min, t, n, r, o), e.max = Ss(e.max, t, n, r, o)
}

function Ua(e, {
    x: t,
    y: n
}) {
    Zr(e.x, t.translate, t.scale, t.originPoint), Zr(e.y, n.translate, n.scale, n.originPoint)
}

function Bd(e, t, n, r = !1) {
    const o = n.length;
    if (!o) return;
    t.x = t.y = 1;
    let s, i;
    for (let a = 0; a < o; a++) {
        s = n[a], i = s.projectionDelta;
        const c = s.instance;
        c && c.style && c.style.display === "contents" || (r && s.options.layoutScroll && s.scroll && s !== s.root && Pt(e, {
            x: -s.scroll.offset.x,
            y: -s.scroll.offset.y
        }), i && (t.x *= i.x.scale, t.y *= i.y.scale, Ua(e, i)), r && ct(s.latestValues) && Pt(e, s.latestValues))
    }
    t.x = Es(t.x), t.y = Es(t.y)
}

function Es(e) {
    return Number.isInteger(e) || e > 1.0000000000001 || e < .999999999999 ? e : 1
}

function Xe(e, t) {
    e.min = e.min + t, e.max = e.max + t
}

function Ts(e, t, [n, r, o]) {
    const s = t[o] !== void 0 ? t[o] : .5,
        i = J(e.min, e.max, s);
    Zr(e, t[n], t[r], i, t.scale)
}
const Ud = ["x", "scaleX", "originX"],
    Wd = ["y", "scaleY", "originY"];

function Pt(e, t) {
    Ts(e.x, t, Ud), Ts(e.y, t, Wd)
}

function Wa(e, t) {
    return ja(jd(e.getBoundingClientRect(), t))
}

function Hd(e, t, n) {
    const r = Wa(e, n),
        {
            scroll: o
        } = t;
    return o && (Xe(r.x, o.offset.x), Xe(r.y, o.offset.y)), r
}
const Gd = new WeakMap;
class Kd {
    constructor(t) {
        this.openGlobalLock = null, this.isDragging = !1, this.currentDirection = null, this.originPoint = {
            x: 0,
            y: 0
        }, this.constraints = !1, this.hasMutatedConstraints = !1, this.elastic = ne(), this.visualElement = t
    }
    start(t, {
        snapToCursor: n = !1
    } = {}) {
        const {
            presenceContext: r
        } = this.visualElement;
        if (r && r.isPresent === !1) return;
        const o = c => {
                this.stopAnimation(), n && this.snapToCursor(Jn(c, "page").point)
            },
            s = (c, l) => {
                const {
                    drag: f,
                    dragPropagation: d,
                    onDragStart: h
                } = this.getProps();
                if (f && !d && (this.openGlobalLock && this.openGlobalLock(), this.openGlobalLock = ra(f), !this.openGlobalLock)) return;
                this.isDragging = !0, this.currentDirection = null, this.resolveConstraints(), this.visualElement.projection && (this.visualElement.projection.isAnimationBlocked = !0, this.visualElement.projection.target = void 0), De(g => {
                    let m = this.getAxisMotionValue(g).get() || 0;
                    if (Oe.test(m)) {
                        const {
                            projection: b
                        } = this.visualElement;
                        if (b && b.layout) {
                            const x = b.layout.layoutBox[g];
                            x && (m = be(x) * (parseFloat(m) / 100))
                        }
                    }
                    this.originPoint[g] = m
                }), h && Q.update(() => h(c, l), !1, !0);
                const {
                    animationState: p
                } = this.visualElement;
                p && p.setActive("whileDrag", !0)
            },
            i = (c, l) => {
                const {
                    dragPropagation: f,
                    dragDirectionLock: d,
                    onDirectionLock: h,
                    onDrag: p
                } = this.getProps();
                if (!f && !this.openGlobalLock) return;
                const {
                    offset: g
                } = l;
                if (d && this.currentDirection === null) {
                    this.currentDirection = zd(g), this.currentDirection !== null && h && h(this.currentDirection);
                    return
                }
                this.updateAxis("x", l.point, g), this.updateAxis("y", l.point, g), this.visualElement.render(), p && p(c, l)
            },
            a = (c, l) => this.stop(c, l);
        this.panSession = new Na(t, {
            onSessionStart: o,
            onStart: s,
            onMove: i,
            onSessionEnd: a
        }, {
            transformPagePoint: this.visualElement.getTransformPagePoint()
        })
    }
    stop(t, n) {
        const r = this.isDragging;
        if (this.cancel(), !r) return;
        const {
            velocity: o
        } = n;
        this.startAnimation(o);
        const {
            onDragEnd: s
        } = this.getProps();
        s && Q.update(() => s(t, n))
    }
    cancel() {
        this.isDragging = !1;
        const {
            projection: t,
            animationState: n
        } = this.visualElement;
        t && (t.isAnimationBlocked = !1), this.panSession && this.panSession.end(), this.panSession = void 0;
        const {
            dragPropagation: r
        } = this.getProps();
        !r && this.openGlobalLock && (this.openGlobalLock(), this.openGlobalLock = null), n && n.setActive("whileDrag", !1)
    }
    updateAxis(t, n, r) {
        const {
            drag: o
        } = this.getProps();
        if (!r || !gn(t, o, this.currentDirection)) return;
        const s = this.getAxisMotionValue(t);
        let i = this.originPoint[t] + r[t];
        this.constraints && this.constraints[t] && (i = _d(i, this.constraints[t], this.elastic[t])), s.set(i)
    }
    resolveConstraints() {
        const {
            dragConstraints: t,
            dragElastic: n
        } = this.getProps(), {
            layout: r
        } = this.visualElement.projection || {}, o = this.constraints;
        t && Ct(t) ? this.constraints || (this.constraints = this.resolveRefConstraints()) : t && r ? this.constraints = kd(r.layoutBox, t) : this.constraints = !1, this.elastic = Nd(n), o !== this.constraints && r && this.constraints && !this.hasMutatedConstraints && De(s => {
            this.getAxisMotionValue(s) && (this.constraints[s] = Vd(r.layoutBox[s], this.constraints[s]))
        })
    }
    resolveRefConstraints() {
        const {
            dragConstraints: t,
            onMeasureDragConstraints: n
        } = this.getProps();
        if (!t || !Ct(t)) return !1;
        const r = t.current,
            {
                projection: o
            } = this.visualElement;
        if (!o || !o.layout) return !1;
        const s = Hd(r, o.root, this.visualElement.getTransformPagePoint());
        let i = Ld(o.layout.layoutBox, s);
        if (n) {
            const a = n(Fd(i));
            this.hasMutatedConstraints = !!a, a && (i = ja(a))
        }
        return i
    }
    startAnimation(t) {
        const {
            drag: n,
            dragMomentum: r,
            dragElastic: o,
            dragTransition: s,
            dragSnapToOrigin: i,
            onDragTransitionEnd: a
        } = this.getProps(), c = this.constraints || {}, l = De(f => {
            if (!gn(f, n, this.currentDirection)) return;
            let d = c && c[f] || {};
            i && (d = {
                min: 0,
                max: 0
            });
            const h = o ? 200 : 1e6,
                p = o ? 40 : 1e7,
                g = {
                    type: "inertia",
                    velocity: r ? t[f] : 0,
                    bounceStiffness: h,
                    bounceDamping: p,
                    timeConstant: 750,
                    restDelta: 1,
                    restSpeed: 10,
                    ...s,
                    ...d
                };
            return this.startAxisValueAnimation(f, g)
        });
        return Promise.all(l).then(a)
    }
    startAxisValueAnimation(t, n) {
        const r = this.getAxisMotionValue(t);
        return r.start(To(t, r, 0, n))
    }
    stopAnimation() {
        De(t => this.getAxisMotionValue(t).stop())
    }
    getAxisMotionValue(t) {
        const n = "_drag" + t.toUpperCase(),
            r = this.visualElement.getProps(),
            o = r[n];
        return o || this.visualElement.getValue(t, (r.initial ? r.initial[t] : void 0) || 0)
    }
    snapToCursor(t) {
        De(n => {
            const {
                drag: r
            } = this.getProps();
            if (!gn(n, r, this.currentDirection)) return;
            const {
                projection: o
            } = this.visualElement, s = this.getAxisMotionValue(n);
            if (o && o.layout) {
                const {
                    min: i,
                    max: a
                } = o.layout.layoutBox[n];
                s.set(t[n] - J(i, a, .5))
            }
        })
    }
    scalePositionWithinConstraints() {
        if (!this.visualElement.current) return;
        const {
            drag: t,
            dragConstraints: n
        } = this.getProps(), {
            projection: r
        } = this.visualElement;
        if (!Ct(n) || !r || !this.constraints) return;
        this.stopAnimation();
        const o = {
            x: 0,
            y: 0
        };
        De(i => {
            const a = this.getAxisMotionValue(i);
            if (a) {
                const c = a.get();
                o[i] = Id({
                    min: c,
                    max: c
                }, this.constraints[i])
            }
        });
        const {
            transformTemplate: s
        } = this.visualElement.getProps();
        this.visualElement.current.style.transform = s ? s({}, "") : "none", r.root && r.root.updateScroll(), r.updateLayout(), this.resolveConstraints(), De(i => {
            if (!gn(i, t, null)) return;
            const a = this.getAxisMotionValue(i),
                {
                    min: c,
                    max: l
                } = this.constraints[i];
            a.set(J(c, l, o[i]))
        })
    }
    addListeners() {
        if (!this.visualElement.current) return;
        Gd.set(this.visualElement, this);
        const t = this.visualElement.current,
            n = Ne(t, "pointerdown", c => {
                const {
                    drag: l,
                    dragListener: f = !0
                } = this.getProps();
                l && f && this.start(c)
            }),
            r = () => {
                const {
                    dragConstraints: c
                } = this.getProps();
                Ct(c) && (this.constraints = this.resolveRefConstraints())
            },
            {
                projection: o
            } = this.visualElement,
            s = o.addEventListener("measure", r);
        o && !o.layout && (o.root && o.root.updateScroll(), o.updateLayout()), r();
        const i = Ve(window, "resize", () => this.scalePositionWithinConstraints()),
            a = o.addEventListener("didUpdate", ({
                delta: c,
                hasLayoutChanged: l
            }) => {
                this.isDragging && l && (De(f => {
                    const d = this.getAxisMotionValue(f);
                    d && (this.originPoint[f] += c[f].translate, d.set(d.get() + c[f].translate))
                }), this.visualElement.render())
            });
        return () => {
            i(), n(), s(), a && a()
        }
    }
    getProps() {
        const t = this.visualElement.getProps(),
            {
                drag: n = !1,
                dragDirectionLock: r = !1,
                dragPropagation: o = !1,
                dragConstraints: s = !1,
                dragElastic: i = Yr,
                dragMomentum: a = !0
            } = t;
        return {
            ...t,
            drag: n,
            dragDirectionLock: r,
            dragPropagation: o,
            dragConstraints: s,
            dragElastic: i,
            dragMomentum: a
        }
    }
}

function gn(e, t, n) {
    return (t === !0 || t === e) && (n === null || n === e)
}

function zd(e, t = 10) {
    let n = null;
    return Math.abs(e.y) > t ? n = "y" : Math.abs(e.x) > t && (n = "x"), n
}
class Yd extends rt {
    constructor(t) {
        super(t), this.removeGroupControls = ee, this.removeListeners = ee, this.controls = new Kd(t)
    }
    mount() {
        const {
            dragControls: t
        } = this.node.getProps();
        t && (this.removeGroupControls = t.subscribe(this.controls)), this.removeListeners = this.controls.addListeners() || ee
    }
    unmount() {
        this.removeGroupControls(), this.removeListeners()
    }
}
const Ms = e => (t, n) => {
    e && Q.update(() => e(t, n))
};
class Xd extends rt {
    constructor() {
        super(...arguments), this.removePointerDownListener = ee
    }
    onPointerDown(t) {
        this.session = new Na(t, this.createPanHandlers(), {
            transformPagePoint: this.node.getTransformPagePoint()
        })
    }
    createPanHandlers() {
        const {
            onPanSessionStart: t,
            onPanStart: n,
            onPan: r,
            onPanEnd: o
        } = this.node.getProps();
        return {
            onSessionStart: Ms(t),
            onStart: Ms(n),
            onMove: r,
            onEnd: (s, i) => {
                delete this.session, o && Q.update(() => o(s, i))
            }
        }
    }
    mount() {
        this.removePointerDownListener = Ne(this.node.current, "pointerdown", t => this.onPointerDown(t))
    }
    update() {
        this.session && this.session.updateHandlers(this.createPanHandlers())
    }
    unmount() {
        this.removePointerDownListener(), this.session && this.session.end()
    }
}

function Zd() {
    const e = u.useContext(Yn);
    if (e === null) return [!0, null];
    const {
        isPresent: t,
        onExitComplete: n,
        register: r
    } = e, o = u.useId();
    return u.useEffect(() => r(o), []), !t && n ? [!1, () => n && n(o)] : [!0]
}
const Sn = {
    hasAnimatedSinceResize: !0,
    hasEverUpdated: !1
};

function Ds(e, t) {
    return t.max === t.min ? 0 : e / (t.max - t.min) * 100
}
const Bt = {
        correct: (e, t) => {
            if (!t.target) return e;
            if (typeof e == "string")
                if (B.test(e)) e = parseFloat(e);
                else return e;
            const n = Ds(e, t.target.x),
                r = Ds(e, t.target.y);
            return `${n}% ${r}%`
        }
    },
    qd = {
        correct: (e, {
            treeScale: t,
            projectionDelta: n
        }) => {
            const r = e,
                o = Je.parse(e);
            if (o.length > 5) return r;
            const s = Je.createTransformer(e),
                i = typeof o[0] != "number" ? 1 : 0,
                a = n.x.scale * t.x,
                c = n.y.scale * t.y;
            o[0 + i] /= a, o[1 + i] /= c;
            const l = J(a, c, .5);
            return typeof o[2 + i] == "number" && (o[2 + i] /= l), typeof o[3 + i] == "number" && (o[3 + i] /= l), s(o)
        }
    };
class Qd extends me.Component {
    componentDidMount() {
        const {
            visualElement: t,
            layoutGroup: n,
            switchLayoutGroup: r,
            layoutId: o
        } = this.props, {
            projection: s
        } = t;
        ru(Jd), s && (n.group && n.group.add(s), r && r.register && o && r.register(s), s.root.didUpdate(), s.addEventListener("animationComplete", () => {
            this.safeToRemove()
        }), s.setOptions({
            ...s.options,
            onExitComplete: () => this.safeToRemove()
        })), Sn.hasEverUpdated = !0
    }
    getSnapshotBeforeUpdate(t) {
        const {
            layoutDependency: n,
            visualElement: r,
            drag: o,
            isPresent: s
        } = this.props, i = r.projection;
        return i && (i.isPresent = s, o || t.layoutDependency !== n || n === void 0 ? i.willUpdate() : this.safeToRemove(), t.isPresent !== s && (s ? i.promote() : i.relegate() || Q.postRender(() => {
            const a = i.getStack();
            (!a || !a.members.length) && this.safeToRemove()
        }))), null
    }
    componentDidUpdate() {
        const {
            projection: t
        } = this.props.visualElement;
        t && (t.root.didUpdate(), queueMicrotask(() => {
            !t.currentAnimation && t.isLead() && this.safeToRemove()
        }))
    }
    componentWillUnmount() {
        const {
            visualElement: t,
            layoutGroup: n,
            switchLayoutGroup: r
        } = this.props, {
            projection: o
        } = t;
        o && (o.scheduleCheckAfterUnmount(), n && n.group && n.group.remove(o), r && r.deregister && r.deregister(o))
    }
    safeToRemove() {
        const {
            safeToRemove: t
        } = this.props;
        t && t()
    }
    render() {
        return null
    }
}

function Ha(e) {
    const [t, n] = Zd(), r = u.useContext(po);
    return me.createElement(Qd, {
        ...e,
        layoutGroup: r,
        switchLayoutGroup: u.useContext(ji),
        isPresent: t,
        safeToRemove: n
    })
}
const Jd = {
        borderRadius: {
            ...Bt,
            applyTo: ["borderTopLeftRadius", "borderTopRightRadius", "borderBottomLeftRadius", "borderBottomRightRadius"]
        },
        borderTopLeftRadius: Bt,
        borderTopRightRadius: Bt,
        borderBottomLeftRadius: Bt,
        borderBottomRightRadius: Bt,
        boxShadow: qd
    },
    Ga = ["TopLeft", "TopRight", "BottomLeft", "BottomRight"],
    eh = Ga.length,
    Rs = e => typeof e == "string" ? parseFloat(e) : e,
    Os = e => typeof e == "number" || B.test(e);

function th(e, t, n, r, o, s) {
    o ? (e.opacity = J(0, n.opacity !== void 0 ? n.opacity : 1, nh(r)), e.opacityExit = J(t.opacity !== void 0 ? t.opacity : 1, 0, rh(r))) : s && (e.opacity = J(t.opacity !== void 0 ? t.opacity : 1, n.opacity !== void 0 ? n.opacity : 1, r));
    for (let i = 0; i < eh; i++) {
        const a = `border${Ga[i]}Radius`;
        let c = _s(t, a),
            l = _s(n, a);
        if (c === void 0 && l === void 0) continue;
        c || (c = 0), l || (l = 0), c === 0 || l === 0 || Os(c) === Os(l) ? (e[a] = Math.max(J(Rs(c), Rs(l), r), 0), (Oe.test(l) || Oe.test(c)) && (e[a] += "%")) : e[a] = l
    }(t.rotate || n.rotate) && (e.rotate = J(t.rotate || 0, n.rotate || 0, r))
}

function _s(e, t) {
    return e[t] !== void 0 ? e[t] : e.borderRadius
}
const nh = Ka(0, .5, $o),
    rh = Ka(.5, .95, ee);

function Ka(e, t, n) {
    return r => r < e ? 0 : r > t ? 1 : n(Qt(e, t, r))
}

function ks(e, t) {
    e.min = t.min, e.max = t.max
}

function xe(e, t) {
    ks(e.x, t.x), ks(e.y, t.y)
}

function Ls(e, t, n, r, o) {
    return e -= t, e = Nn(e, 1 / n, r), o !== void 0 && (e = Nn(e, 1 / o, r)), e
}

function oh(e, t = 0, n = 1, r = .5, o, s = e, i = e) {
    if (Oe.test(t) && (t = parseFloat(t), t = J(i.min, i.max, t / 100) - i.min), typeof t != "number") return;
    let a = J(s.min, s.max, r);
    e === s && (a -= t), e.min = Ls(e.min, t, n, a, o), e.max = Ls(e.max, t, n, a, o)
}

function Is(e, t, [n, r, o], s, i) {
    oh(e, t[n], t[r], t[o], t.scale, s, i)
}
const sh = ["x", "scaleX", "originX"],
    ih = ["y", "scaleY", "originY"];

function Vs(e, t, n, r) {
    Is(e.x, t, sh, n ? n.x : void 0, r ? r.x : void 0), Is(e.y, t, ih, n ? n.y : void 0, r ? r.y : void 0)
}

function Ns(e) {
    return e.translate === 0 && e.scale === 1
}

function za(e) {
    return Ns(e.x) && Ns(e.y)
}

function ah(e, t) {
    return e.x.min === t.x.min && e.x.max === t.x.max && e.y.min === t.y.min && e.y.max === t.y.max
}

function Ya(e, t) {
    return Math.round(e.x.min) === Math.round(t.x.min) && Math.round(e.x.max) === Math.round(t.x.max) && Math.round(e.y.min) === Math.round(t.y.min) && Math.round(e.y.max) === Math.round(t.y.max)
}

function Fs(e) {
    return be(e.x) / be(e.y)
}
class ch {
    constructor() {
        this.members = []
    }
    add(t) {
        Mo(this.members, t), t.scheduleRender()
    }
    remove(t) {
        if (Do(this.members, t), t === this.prevLead && (this.prevLead = void 0), t === this.lead) {
            const n = this.members[this.members.length - 1];
            n && this.promote(n)
        }
    }
    relegate(t) {
        const n = this.members.findIndex(o => t === o);
        if (n === 0) return !1;
        let r;
        for (let o = n; o >= 0; o--) {
            const s = this.members[o];
            if (s.isPresent !== !1) {
                r = s;
                break
            }
        }
        return r ? (this.promote(r), !0) : !1
    }
    promote(t, n) {
        const r = this.lead;
        if (t !== r && (this.prevLead = r, this.lead = t, t.show(), r)) {
            r.instance && r.scheduleRender(), t.scheduleRender(), t.resumeFrom = r, n && (t.resumeFrom.preserveOpacity = !0), r.snapshot && (t.snapshot = r.snapshot, t.snapshot.latestValues = r.animationValues || r.latestValues), t.root && t.root.isUpdating && (t.isLayoutDirty = !0);
            const {
                crossfade: o
            } = t.options;
            o === !1 && r.hide()
        }
    }
    exitAnimationComplete() {
        this.members.forEach(t => {
            const {
                options: n,
                resumingFrom: r
            } = t;
            n.onExitComplete && n.onExitComplete(), r && r.options.onExitComplete && r.options.onExitComplete()
        })
    }
    scheduleRender() {
        this.members.forEach(t => {
            t.instance && t.scheduleRender(!1)
        })
    }
    removeLeadSnapshot() {
        this.lead && this.lead.snapshot && (this.lead.snapshot = void 0)
    }
}

function js(e, t, n) {
    let r = "";
    const o = e.x.translate / t.x,
        s = e.y.translate / t.y;
    if ((o || s) && (r = `translate3d(${o}px, ${s}px, 0) `), (t.x !== 1 || t.y !== 1) && (r += `scale(${1/t.x}, ${1/t.y}) `), n) {
        const {
            rotate: c,
            rotateX: l,
            rotateY: f
        } = n;
        c && (r += `rotate(${c}deg) `), l && (r += `rotateX(${l}deg) `), f && (r += `rotateY(${f}deg) `)
    }
    const i = e.x.scale * t.x,
        a = e.y.scale * t.y;
    return (i !== 1 || a !== 1) && (r += `scale(${i}, ${a})`), r || "none"
}
const lh = (e, t) => e.depth - t.depth;
class uh {
    constructor() {
        this.children = [], this.isDirty = !1
    }
    add(t) {
        Mo(this.children, t), this.isDirty = !0
    }
    remove(t) {
        Do(this.children, t), this.isDirty = !0
    }
    forEach(t) {
        this.isDirty && this.children.sort(lh), this.isDirty = !1, this.children.forEach(t)
    }
}

function fh(e, t) {
    const n = performance.now(),
        r = ({
            timestamp: o
        }) => {
            const s = o - n;
            s >= t && (je(r), e(s - t))
        };
    return Q.read(r, !0), () => je(r)
}

function dh(e) {
    window.MotionDebug && window.MotionDebug.record(e)
}

function hh(e) {
    return e instanceof SVGElement && e.tagName !== "svg"
}

function ph(e, t, n) {
    const r = fe(e) ? e : Mt(e);
    return r.start(To("", r, t, n)), r.animation
}
const Bs = ["", "X", "Y", "Z"],
    Us = 1e3;
let mh = 0;
const lt = {
    type: "projectionFrame",
    totalNodes: 0,
    resolvedTargetDeltas: 0,
    recalculatedProjection: 0
};

function Xa({
    attachResizeListener: e,
    defaultParent: t,
    measureScroll: n,
    checkIsScrollRoot: r,
    resetTransform: o
}) {
    return class {
        constructor(i = {}, a = t?.()) {
            this.id = mh++, this.animationId = 0, this.children = new Set, this.options = {}, this.isTreeAnimating = !1, this.isAnimationBlocked = !1, this.isLayoutDirty = !1, this.isProjectionDirty = !1, this.isSharedProjectionDirty = !1, this.isTransformDirty = !1, this.updateManuallyBlocked = !1, this.updateBlockedByResize = !1, this.isUpdating = !1, this.isSVG = !1, this.needsReset = !1, this.shouldResetTransform = !1, this.treeScale = {
                x: 1,
                y: 1
            }, this.eventHandlers = new Map, this.hasTreeAnimated = !1, this.updateScheduled = !1, this.checkUpdateFailed = () => {
                this.isUpdating && (this.isUpdating = !1, this.clearAllSnapshots())
            }, this.updateProjection = () => {
                lt.totalNodes = lt.resolvedTargetDeltas = lt.recalculatedProjection = 0, this.nodes.forEach(yh), this.nodes.forEach(Ah), this.nodes.forEach($h), this.nodes.forEach(bh), dh(lt)
            }, this.hasProjected = !1, this.isVisible = !0, this.animationProgress = 0, this.sharedNodes = new Map, this.latestValues = i, this.root = a ? a.root || a : this, this.path = a ? [...a.path, a] : [], this.parent = a, this.depth = a ? a.depth + 1 : 0;
            for (let c = 0; c < this.path.length; c++) this.path[c].shouldResetTransform = !0;
            this.root === this && (this.nodes = new uh)
        }
        addEventListener(i, a) {
            return this.eventHandlers.has(i) || this.eventHandlers.set(i, new Ro), this.eventHandlers.get(i).add(a)
        }
        notifyListeners(i, ...a) {
            const c = this.eventHandlers.get(i);
            c && c.notify(...a)
        }
        hasListeners(i) {
            return this.eventHandlers.has(i)
        }
        mount(i, a = this.root.hasTreeAnimated) {
            if (this.instance) return;
            this.isSVG = hh(i), this.instance = i;
            const {
                layoutId: c,
                layout: l,
                visualElement: f
            } = this.options;
            if (f && !f.current && f.mount(i), this.root.nodes.add(this), this.parent && this.parent.children.add(this), a && (l || c) && (this.isLayoutDirty = !0), e) {
                let d;
                const h = () => this.root.updateBlockedByResize = !1;
                e(i, () => {
                    this.root.updateBlockedByResize = !0, d && d(), d = fh(h, 250), Sn.hasAnimatedSinceResize && (Sn.hasAnimatedSinceResize = !1, this.nodes.forEach(Hs))
                })
            }
            c && this.root.registerSharedNode(c, this), this.options.animate !== !1 && f && (c || l) && this.addEventListener("didUpdate", ({
                delta: d,
                hasLayoutChanged: h,
                hasRelativeTargetChanged: p,
                layout: g
            }) => {
                if (this.isTreeAnimationBlocked()) {
                    this.target = void 0, this.relativeTarget = void 0;
                    return
                }
                const m = this.options.transition || f.getDefaultTransition() || Mh,
                    {
                        onLayoutAnimationStart: b,
                        onLayoutAnimationComplete: x
                    } = f.getProps(),
                    v = !this.targetLayout || !Ya(this.targetLayout, g) || p,
                    y = !h && p;
                if (this.options.layoutRoot || this.resumeFrom && this.resumeFrom.instance || y || h && (v || !this.currentAnimation)) {
                    this.resumeFrom && (this.resumingFrom = this.resumeFrom, this.resumingFrom.resumingFrom = void 0), this.setAnimationOrigin(d, y);
                    const C = {
                        ...ka(m, "layout"),
                        onPlay: b,
                        onComplete: x
                    };
                    (f.shouldReduceMotion || this.options.layoutRoot) && (C.delay = 0, C.type = !1), this.startAnimation(C)
                } else h || Hs(this), this.isLead() && this.options.onExitComplete && this.options.onExitComplete();
                this.targetLayout = g
            })
        }
        unmount() {
            this.options.layoutId && this.willUpdate(), this.root.nodes.remove(this);
            const i = this.getStack();
            i && i.remove(this), this.parent && this.parent.children.delete(this), this.instance = void 0, je(this.updateProjection)
        }
        blockUpdate() {
            this.updateManuallyBlocked = !0
        }
        unblockUpdate() {
            this.updateManuallyBlocked = !1
        }
        isUpdateBlocked() {
            return this.updateManuallyBlocked || this.updateBlockedByResize
        }
        isTreeAnimationBlocked() {
            return this.isAnimationBlocked || this.parent && this.parent.isTreeAnimationBlocked() || !1
        }
        startUpdate() {
            this.isUpdateBlocked() || (this.isUpdating = !0, this.nodes && this.nodes.forEach(Ph), this.animationId++)
        }
        getTransformTemplate() {
            const {
                visualElement: i
            } = this.options;
            return i && i.getProps().transformTemplate
        }
        willUpdate(i = !0) {
            if (this.root.hasTreeAnimated = !0, this.root.isUpdateBlocked()) {
                this.options.onExitComplete && this.options.onExitComplete();
                return
            }
            if (!this.root.isUpdating && this.root.startUpdate(), this.isLayoutDirty) return;
            this.isLayoutDirty = !0;
            for (let f = 0; f < this.path.length; f++) {
                const d = this.path[f];
                d.shouldResetTransform = !0, d.updateScroll("snapshot"), d.options.layoutRoot && d.willUpdate(!1)
            }
            const {
                layoutId: a,
                layout: c
            } = this.options;
            if (a === void 0 && !c) return;
            const l = this.getTransformTemplate();
            this.prevTransformTemplateValue = l ? l(this.latestValues, "") : void 0, this.updateSnapshot(), i && this.notifyListeners("willUpdate")
        }
        update() {
            if (this.updateScheduled = !1, this.isUpdateBlocked()) {
                this.unblockUpdate(), this.clearAllSnapshots(), this.nodes.forEach(Ws);
                return
            }
            this.isUpdating || this.nodes.forEach(wh), this.isUpdating = !1, this.nodes.forEach(Ch), this.nodes.forEach(gh), this.nodes.forEach(vh), this.clearAllSnapshots();
            const a = performance.now();
            ie.delta = Qe(0, 1e3 / 60, a - ie.timestamp), ie.timestamp = a, ie.isProcessing = !0, gr.update.process(ie), gr.preRender.process(ie), gr.render.process(ie), ie.isProcessing = !1
        }
        didUpdate() {
            this.updateScheduled || (this.updateScheduled = !0, queueMicrotask(() => this.update()))
        }
        clearAllSnapshots() {
            this.nodes.forEach(xh), this.sharedNodes.forEach(Sh)
        }
        scheduleUpdateProjection() {
            Q.preRender(this.updateProjection, !1, !0)
        }
        scheduleCheckAfterUnmount() {
            Q.postRender(() => {
                this.isLayoutDirty ? this.root.didUpdate() : this.root.checkUpdateFailed()
            })
        }
        updateSnapshot() {
            this.snapshot || !this.instance || (this.snapshot = this.measure())
        }
        updateLayout() {
            if (!this.instance || (this.updateScroll(), !(this.options.alwaysMeasureLayout && this.isLead()) && !this.isLayoutDirty)) return;
            if (this.resumeFrom && !this.resumeFrom.instance)
                for (let c = 0; c < this.path.length; c++) this.path[c].updateScroll();
            const i = this.layout;
            this.layout = this.measure(!1), this.layoutCorrected = ne(), this.isLayoutDirty = !1, this.projectionDelta = void 0, this.notifyListeners("measure", this.layout.layoutBox);
            const {
                visualElement: a
            } = this.options;
            a && a.notify("LayoutMeasure", this.layout.layoutBox, i ? i.layoutBox : void 0)
        }
        updateScroll(i = "measure") {
            let a = !!(this.options.layoutScroll && this.instance);
            this.scroll && this.scroll.animationId === this.root.animationId && this.scroll.phase === i && (a = !1), a && (this.scroll = {
                animationId: this.root.animationId,
                phase: i,
                isRoot: r(this.instance),
                offset: n(this.instance)
            })
        }
        resetTransform() {
            if (!o) return;
            const i = this.isLayoutDirty || this.shouldResetTransform,
                a = this.projectionDelta && !za(this.projectionDelta),
                c = this.getTransformTemplate(),
                l = c ? c(this.latestValues, "") : void 0,
                f = l !== this.prevTransformTemplateValue;
            i && (a || ct(this.latestValues) || f) && (o(this.instance, l), this.shouldResetTransform = !1, this.scheduleRender())
        }
        measure(i = !0) {
            const a = this.measurePageBox();
            let c = this.removeElementScroll(a);
            return i && (c = this.removeTransform(c)), Dh(c), {
                animationId: this.root.animationId,
                measuredBox: a,
                layoutBox: c,
                latestValues: {},
                source: this.id
            }
        }
        measurePageBox() {
            const {
                visualElement: i
            } = this.options;
            if (!i) return ne();
            const a = i.measureViewportBox(),
                {
                    scroll: c
                } = this.root;
            return c && (Xe(a.x, c.offset.x), Xe(a.y, c.offset.y)), a
        }
        removeElementScroll(i) {
            const a = ne();
            xe(a, i);
            for (let c = 0; c < this.path.length; c++) {
                const l = this.path[c],
                    {
                        scroll: f,
                        options: d
                    } = l;
                if (l !== this.root && f && d.layoutScroll) {
                    if (f.isRoot) {
                        xe(a, i);
                        const {
                            scroll: h
                        } = this.root;
                        h && (Xe(a.x, -h.offset.x), Xe(a.y, -h.offset.y))
                    }
                    Xe(a.x, f.offset.x), Xe(a.y, f.offset.y)
                }
            }
            return a
        }
        applyTransform(i, a = !1) {
            const c = ne();
            xe(c, i);
            for (let l = 0; l < this.path.length; l++) {
                const f = this.path[l];
                !a && f.options.layoutScroll && f.scroll && f !== f.root && Pt(c, {
                    x: -f.scroll.offset.x,
                    y: -f.scroll.offset.y
                }), ct(f.latestValues) && Pt(c, f.latestValues)
            }
            return ct(this.latestValues) && Pt(c, this.latestValues), c
        }
        removeTransform(i) {
            const a = ne();
            xe(a, i);
            for (let c = 0; c < this.path.length; c++) {
                const l = this.path[c];
                if (!l.instance || !ct(l.latestValues)) continue;
                Xr(l.latestValues) && l.updateSnapshot();
                const f = ne(),
                    d = l.measurePageBox();
                xe(f, d), Vs(a, l.latestValues, l.snapshot ? l.snapshot.layoutBox : void 0, f)
            }
            return ct(this.latestValues) && Vs(a, this.latestValues), a
        }
        setTargetDelta(i) {
            this.targetDelta = i, this.root.scheduleUpdateProjection(), this.isProjectionDirty = !0
        }
        setOptions(i) {
            this.options = {
                ...this.options,
                ...i,
                crossfade: i.crossfade !== void 0 ? i.crossfade : !0
            }
        }
        clearMeasurements() {
            this.scroll = void 0, this.layout = void 0, this.snapshot = void 0, this.prevTransformTemplateValue = void 0, this.targetDelta = void 0, this.target = void 0, this.isLayoutDirty = !1
        }
        forceRelativeParentToResolveTarget() {
            this.relativeParent && this.relativeParent.resolvedRelativeTargetAt !== ie.timestamp && this.relativeParent.resolveTargetDelta(!0)
        }
        resolveTargetDelta(i = !1) {
            var a;
            const c = this.getLead();
            this.isProjectionDirty || (this.isProjectionDirty = c.isProjectionDirty), this.isTransformDirty || (this.isTransformDirty = c.isTransformDirty), this.isSharedProjectionDirty || (this.isSharedProjectionDirty = c.isSharedProjectionDirty);
            const l = !!this.resumingFrom || this !== c;
            if (!(i || l && this.isSharedProjectionDirty || this.isProjectionDirty || !((a = this.parent) === null || a === void 0) && a.isProjectionDirty || this.attemptToResolveRelativeTarget)) return;
            const {
                layout: d,
                layoutId: h
            } = this.options;
            if (!(!this.layout || !(d || h))) {
                if (this.resolvedRelativeTargetAt = ie.timestamp, !this.targetDelta && !this.relativeTarget) {
                    const p = this.getClosestProjectingParent();
                    p && p.layout && this.animationProgress !== 1 ? (this.relativeParent = p, this.forceRelativeParentToResolveTarget(), this.relativeTarget = ne(), this.relativeTargetOrigin = ne(), Yt(this.relativeTargetOrigin, this.layout.layoutBox, p.layout.layoutBox), xe(this.relativeTarget, this.relativeTargetOrigin)) : this.relativeParent = this.relativeTarget = void 0
                }
                if (!(!this.relativeTarget && !this.targetDelta)) {
                    if (this.target || (this.target = ne(), this.targetWithTransforms = ne()), this.relativeTarget && this.relativeTargetOrigin && this.relativeParent && this.relativeParent.target ? (this.forceRelativeParentToResolveTarget(), Od(this.target, this.relativeTarget, this.relativeParent.target)) : this.targetDelta ? (this.resumingFrom ? this.target = this.applyTransform(this.layout.layoutBox) : xe(this.target, this.layout.layoutBox), Ua(this.target, this.targetDelta)) : xe(this.target, this.layout.layoutBox), this.attemptToResolveRelativeTarget) {
                        this.attemptToResolveRelativeTarget = !1;
                        const p = this.getClosestProjectingParent();
                        p && !!p.resumingFrom == !!this.resumingFrom && !p.options.layoutScroll && p.target && this.animationProgress !== 1 ? (this.relativeParent = p, this.forceRelativeParentToResolveTarget(), this.relativeTarget = ne(), this.relativeTargetOrigin = ne(), Yt(this.relativeTargetOrigin, this.target, p.target), xe(this.relativeTarget, this.relativeTargetOrigin)) : this.relativeParent = this.relativeTarget = void 0
                    }
                    lt.resolvedTargetDeltas++
                }
            }
        }
        getClosestProjectingParent() {
            if (!(!this.parent || Xr(this.parent.latestValues) || Ba(this.parent.latestValues))) return this.parent.isProjecting() ? this.parent : this.parent.getClosestProjectingParent()
        }
        isProjecting() {
            return !!((this.relativeTarget || this.targetDelta || this.options.layoutRoot) && this.layout)
        }
        calcProjection() {
            var i;
            const a = this.getLead(),
                c = !!this.resumingFrom || this !== a;
            let l = !0;
            if ((this.isProjectionDirty || !((i = this.parent) === null || i === void 0) && i.isProjectionDirty) && (l = !1), c && (this.isSharedProjectionDirty || this.isTransformDirty) && (l = !1), this.resolvedRelativeTargetAt === ie.timestamp && (l = !1), l) return;
            const {
                layout: f,
                layoutId: d
            } = this.options;
            if (this.isTreeAnimating = !!(this.parent && this.parent.isTreeAnimating || this.currentAnimation || this.pendingAnimation), this.isTreeAnimating || (this.targetDelta = this.relativeTarget = void 0), !this.layout || !(f || d)) return;
            xe(this.layoutCorrected, this.layout.layoutBox);
            const h = this.treeScale.x,
                p = this.treeScale.y;
            Bd(this.layoutCorrected, this.treeScale, this.path, c), a.layout && !a.target && (this.treeScale.x !== 1 || this.treeScale.y !== 1) && (a.target = a.layout.layoutBox);
            const {
                target: g
            } = a;
            if (!g) {
                this.projectionTransform && (this.projectionDelta = $t(), this.projectionTransform = "none", this.scheduleRender());
                return
            }
            this.projectionDelta || (this.projectionDelta = $t(), this.projectionDeltaWithTransform = $t());
            const m = this.projectionTransform;
            zt(this.projectionDelta, this.layoutCorrected, g, this.latestValues), this.projectionTransform = js(this.projectionDelta, this.treeScale), (this.projectionTransform !== m || this.treeScale.x !== h || this.treeScale.y !== p) && (this.hasProjected = !0, this.scheduleRender(), this.notifyListeners("projectionUpdate", g)), lt.recalculatedProjection++
        }
        hide() {
            this.isVisible = !1
        }
        show() {
            this.isVisible = !0
        }
        scheduleRender(i = !0) {
            if (this.options.scheduleRender && this.options.scheduleRender(), i) {
                const a = this.getStack();
                a && a.scheduleRender()
            }
            this.resumingFrom && !this.resumingFrom.instance && (this.resumingFrom = void 0)
        }
        setAnimationOrigin(i, a = !1) {
            const c = this.snapshot,
                l = c ? c.latestValues : {},
                f = {
                    ...this.latestValues
                },
                d = $t();
            (!this.relativeParent || !this.relativeParent.options.layoutRoot) && (this.relativeTarget = this.relativeTargetOrigin = void 0), this.attemptToResolveRelativeTarget = !a;
            const h = ne(),
                p = c ? c.source : void 0,
                g = this.layout ? this.layout.source : void 0,
                m = p !== g,
                b = this.getStack(),
                x = !b || b.members.length <= 1,
                v = !!(m && !x && this.options.crossfade === !0 && !this.path.some(Th));
            this.animationProgress = 0;
            let y;
            this.mixTargetDelta = C => {
                const $ = C / 1e3;
                Gs(d.x, i.x, $), Gs(d.y, i.y, $), this.setTargetDelta(d), this.relativeTarget && this.relativeTargetOrigin && this.layout && this.relativeParent && this.relativeParent.layout && (Yt(h, this.layout.layoutBox, this.relativeParent.layout.layoutBox), Eh(this.relativeTarget, this.relativeTargetOrigin, h, $), y && ah(this.relativeTarget, y) && (this.isProjectionDirty = !1), y || (y = ne()), xe(y, this.relativeTarget)), m && (this.animationValues = f, th(f, l, this.latestValues, $, v, x)), this.root.scheduleUpdateProjection(), this.scheduleRender(), this.animationProgress = $
            }, this.mixTargetDelta(this.options.layoutRoot ? 1e3 : 0)
        }
        startAnimation(i) {
            this.notifyListeners("animationStart"), this.currentAnimation && this.currentAnimation.stop(), this.resumingFrom && this.resumingFrom.currentAnimation && this.resumingFrom.currentAnimation.stop(), this.pendingAnimation && (je(this.pendingAnimation), this.pendingAnimation = void 0), this.pendingAnimation = Q.update(() => {
                Sn.hasAnimatedSinceResize = !0, this.currentAnimation = ph(0, Us, {
                    ...i,
                    onUpdate: a => {
                        this.mixTargetDelta(a), i.onUpdate && i.onUpdate(a)
                    },
                    onComplete: () => {
                        i.onComplete && i.onComplete(), this.completeAnimation()
                    }
                }), this.resumingFrom && (this.resumingFrom.currentAnimation = this.currentAnimation), this.pendingAnimation = void 0
            })
        }
        completeAnimation() {
            this.resumingFrom && (this.resumingFrom.currentAnimation = void 0, this.resumingFrom.preserveOpacity = void 0);
            const i = this.getStack();
            i && i.exitAnimationComplete(), this.resumingFrom = this.currentAnimation = this.animationValues = void 0, this.notifyListeners("animationComplete")
        }
        finishAnimation() {
            this.currentAnimation && (this.mixTargetDelta && this.mixTargetDelta(Us), this.currentAnimation.stop()), this.completeAnimation()
        }
        applyTransformsToTarget() {
            const i = this.getLead();
            let {
                targetWithTransforms: a,
                target: c,
                layout: l,
                latestValues: f
            } = i;
            if (!(!a || !c || !l)) {
                if (this !== i && this.layout && l && Za(this.options.animationType, this.layout.layoutBox, l.layoutBox)) {
                    c = this.target || ne();
                    const d = be(this.layout.layoutBox.x);
                    c.x.min = i.target.x.min, c.x.max = c.x.min + d;
                    const h = be(this.layout.layoutBox.y);
                    c.y.min = i.target.y.min, c.y.max = c.y.min + h
                }
                xe(a, c), Pt(a, f), zt(this.projectionDeltaWithTransform, this.layoutCorrected, a, f)
            }
        }
        registerSharedNode(i, a) {
            this.sharedNodes.has(i) || this.sharedNodes.set(i, new ch), this.sharedNodes.get(i).add(a);
            const l = a.options.initialPromotionConfig;
            a.promote({
                transition: l ? l.transition : void 0,
                preserveFollowOpacity: l && l.shouldPreserveFollowOpacity ? l.shouldPreserveFollowOpacity(a) : void 0
            })
        }
        isLead() {
            const i = this.getStack();
            return i ? i.lead === this : !0
        }
        getLead() {
            var i;
            const {
                layoutId: a
            } = this.options;
            return a ? ((i = this.getStack()) === null || i === void 0 ? void 0 : i.lead) || this : this
        }
        getPrevLead() {
            var i;
            const {
                layoutId: a
            } = this.options;
            return a ? (i = this.getStack()) === null || i === void 0 ? void 0 : i.prevLead : void 0
        }
        getStack() {
            const {
                layoutId: i
            } = this.options;
            if (i) return this.root.sharedNodes.get(i)
        }
        promote({
            needsReset: i,
            transition: a,
            preserveFollowOpacity: c
        } = {}) {
            const l = this.getStack();
            l && l.promote(this, c), i && (this.projectionDelta = void 0, this.needsReset = !0), a && this.setOptions({
                transition: a
            })
        }
        relegate() {
            const i = this.getStack();
            return i ? i.relegate(this) : !1
        }
        resetRotation() {
            const {
                visualElement: i
            } = this.options;
            if (!i) return;
            let a = !1;
            const {
                latestValues: c
            } = i;
            if ((c.rotate || c.rotateX || c.rotateY || c.rotateZ) && (a = !0), !a) return;
            const l = {};
            for (let f = 0; f < Bs.length; f++) {
                const d = "rotate" + Bs[f];
                c[d] && (l[d] = c[d], i.setStaticValue(d, 0))
            }
            i.render();
            for (const f in l) i.setStaticValue(f, l[f]);
            i.scheduleRender()
        }
        getProjectionStyles(i = {}) {
            var a, c;
            const l = {};
            if (!this.instance || this.isSVG) return l;
            if (this.isVisible) l.visibility = "";
            else return {
                visibility: "hidden"
            };
            const f = this.getTransformTemplate();
            if (this.needsReset) return this.needsReset = !1, l.opacity = "", l.pointerEvents = Pn(i.pointerEvents) || "", l.transform = f ? f(this.latestValues, "") : "none", l;
            const d = this.getLead();
            if (!this.projectionDelta || !this.layout || !d.target) {
                const m = {};
                return this.options.layoutId && (m.opacity = this.latestValues.opacity !== void 0 ? this.latestValues.opacity : 1, m.pointerEvents = Pn(i.pointerEvents) || ""), this.hasProjected && !ct(this.latestValues) && (m.transform = f ? f({}, "") : "none", this.hasProjected = !1), m
            }
            const h = d.animationValues || d.latestValues;
            this.applyTransformsToTarget(), l.transform = js(this.projectionDeltaWithTransform, this.treeScale, h), f && (l.transform = f(h, l.transform));
            const {
                x: p,
                y: g
            } = this.projectionDelta;
            l.transformOrigin = `${p.origin*100}% ${g.origin*100}% 0`, d.animationValues ? l.opacity = d === this ? (c = (a = h.opacity) !== null && a !== void 0 ? a : this.latestValues.opacity) !== null && c !== void 0 ? c : 1 : this.preserveOpacity ? this.latestValues.opacity : h.opacityExit : l.opacity = d === this ? h.opacity !== void 0 ? h.opacity : "" : h.opacityExit !== void 0 ? h.opacityExit : 0;
            for (const m in Rn) {
                if (h[m] === void 0) continue;
                const {
                    correct: b,
                    applyTo: x
                } = Rn[m], v = l.transform === "none" ? h[m] : b(h[m], d);
                if (x) {
                    const y = x.length;
                    for (let C = 0; C < y; C++) l[x[C]] = v
                } else l[m] = v
            }
            return this.options.layoutId && (l.pointerEvents = d === this ? Pn(i.pointerEvents) || "" : "none"), l
        }
        clearSnapshot() {
            this.resumeFrom = this.snapshot = void 0
        }
        resetTree() {
            this.root.nodes.forEach(i => {
                var a;
                return (a = i.currentAnimation) === null || a === void 0 ? void 0 : a.stop()
            }), this.root.nodes.forEach(Ws), this.root.sharedNodes.clear()
        }
    }
}

function gh(e) {
    e.updateLayout()
}

function vh(e) {
    var t;
    const n = ((t = e.resumeFrom) === null || t === void 0 ? void 0 : t.snapshot) || e.snapshot;
    if (e.isLead() && e.layout && n && e.hasListeners("didUpdate")) {
        const {
            layoutBox: r,
            measuredBox: o
        } = e.layout, {
            animationType: s
        } = e.options, i = n.source !== e.layout.source;
        s === "size" ? De(d => {
            const h = i ? n.measuredBox[d] : n.layoutBox[d],
                p = be(h);
            h.min = r[d].min, h.max = h.min + p
        }) : Za(s, n.layoutBox, r) && De(d => {
            const h = i ? n.measuredBox[d] : n.layoutBox[d],
                p = be(r[d]);
            h.max = h.min + p, e.relativeTarget && !e.currentAnimation && (e.isProjectionDirty = !0, e.relativeTarget[d].max = e.relativeTarget[d].min + p)
        });
        const a = $t();
        zt(a, r, n.layoutBox);
        const c = $t();
        i ? zt(c, e.applyTransform(o, !0), n.measuredBox) : zt(c, r, n.layoutBox);
        const l = !za(a);
        let f = !1;
        if (!e.resumeFrom) {
            const d = e.getClosestProjectingParent();
            if (d && !d.resumeFrom) {
                const {
                    snapshot: h,
                    layout: p
                } = d;
                if (h && p) {
                    const g = ne();
                    Yt(g, n.layoutBox, h.layoutBox);
                    const m = ne();
                    Yt(m, r, p.layoutBox), Ya(g, m) || (f = !0), d.options.layoutRoot && (e.relativeTarget = m, e.relativeTargetOrigin = g, e.relativeParent = d)
                }
            }
        }
        e.notifyListeners("didUpdate", {
            layout: r,
            snapshot: n,
            delta: c,
            layoutDelta: a,
            hasLayoutChanged: l,
            hasRelativeTargetChanged: f
        })
    } else if (e.isLead()) {
        const {
            onExitComplete: r
        } = e.options;
        r && r()
    }
    e.options.transition = void 0
}

function yh(e) {
    lt.totalNodes++, e.parent && (e.isProjecting() || (e.isProjectionDirty = e.parent.isProjectionDirty), e.isSharedProjectionDirty || (e.isSharedProjectionDirty = !!(e.isProjectionDirty || e.parent.isProjectionDirty || e.parent.isSharedProjectionDirty)), e.isTransformDirty || (e.isTransformDirty = e.parent.isTransformDirty))
}

function bh(e) {
    e.isProjectionDirty = e.isSharedProjectionDirty = e.isTransformDirty = !1
}

function xh(e) {
    e.clearSnapshot()
}

function Ws(e) {
    e.clearMeasurements()
}

function wh(e) {
    e.isLayoutDirty = !1
}

function Ch(e) {
    const {
        visualElement: t
    } = e.options;
    t && t.getProps().onBeforeLayoutMeasure && t.notify("BeforeLayoutMeasure"), e.resetTransform()
}

function Hs(e) {
    e.finishAnimation(), e.targetDelta = e.relativeTarget = e.target = void 0, e.isProjectionDirty = !0
}

function Ah(e) {
    e.resolveTargetDelta()
}

function $h(e) {
    e.calcProjection()
}

function Ph(e) {
    e.resetRotation()
}

function Sh(e) {
    e.removeLeadSnapshot()
}

function Gs(e, t, n) {
    e.translate = J(t.translate, 0, n), e.scale = J(t.scale, 1, n), e.origin = t.origin, e.originPoint = t.originPoint
}

function Ks(e, t, n, r) {
    e.min = J(t.min, n.min, r), e.max = J(t.max, n.max, r)
}

function Eh(e, t, n, r) {
    Ks(e.x, t.x, n.x, r), Ks(e.y, t.y, n.y, r)
}

function Th(e) {
    return e.animationValues && e.animationValues.opacityExit !== void 0
}
const Mh = {
        duration: .45,
        ease: [.4, 0, .1, 1]
    },
    zs = e => typeof navigator < "u" && navigator.userAgent.toLowerCase().includes(e),
    Ys = zs("applewebkit/") && !zs("chrome/") ? Math.round : ee;

function Xs(e) {
    e.min = Ys(e.min), e.max = Ys(e.max)
}

function Dh(e) {
    Xs(e.x), Xs(e.y)
}

function Za(e, t, n) {
    return e === "position" || e === "preserve-aspect" && !zr(Fs(t), Fs(n), .2)
}
const Rh = Xa({
        attachResizeListener: (e, t) => Ve(e, "resize", t),
        measureScroll: () => ({
            x: document.documentElement.scrollLeft || document.body.scrollLeft,
            y: document.documentElement.scrollTop || document.body.scrollTop
        }),
        checkIsScrollRoot: () => !0
    }),
    Er = {
        current: void 0
    },
    qa = Xa({
        measureScroll: e => ({
            x: e.scrollLeft,
            y: e.scrollTop
        }),
        defaultParent: () => {
            if (!Er.current) {
                const e = new Rh({});
                e.mount(window), e.setOptions({
                    layoutScroll: !0
                }), Er.current = e
            }
            return Er.current
        },
        resetTransform: (e, t) => {
            e.style.transform = t !== void 0 ? t : "none"
        },
        checkIsScrollRoot: e => window.getComputedStyle(e).position === "fixed"
    }),
    Oh = {
        pan: {
            Feature: Xd
        },
        drag: {
            Feature: Yd,
            ProjectionNode: qa,
            MeasureLayout: Ha
        }
    },
    _h = /var\((--[a-zA-Z0-9-_]+),? ?([a-zA-Z0-9 ()%#.,-]+)?\)/;

function kh(e) {
    const t = _h.exec(e);
    if (!t) return [, ];
    const [, n, r] = t;
    return [n, r]
}

function qr(e, t, n = 1) {
    const [r, o] = kh(e);
    if (!r) return;
    const s = window.getComputedStyle(t).getPropertyValue(r);
    return s ? s.trim() : jr(o) ? qr(o, t, n + 1) : o
}

function Lh(e, {
    ...t
}, n) {
    const r = e.current;
    if (!(r instanceof Element)) return {
        target: t,
        transitionEnd: n
    };
    n && (n = {
        ...n
    }), e.values.forEach(o => {
        const s = o.get();
        if (!jr(s)) return;
        const i = qr(s, r);
        i && o.set(i)
    });
    for (const o in t) {
        const s = t[o];
        if (!jr(s)) continue;
        const i = qr(s, r);
        i && (t[o] = i, n || (n = {}), n[o] === void 0 && (n[o] = s))
    }
    return {
        target: t,
        transitionEnd: n
    }
}
const Ih = new Set(["width", "height", "top", "left", "right", "bottom", "x", "y", "translateX", "translateY"]),
    Qa = e => Ih.has(e),
    Vh = e => Object.keys(e).some(Qa),
    Zs = e => e === ht || e === B,
    qs = (e, t) => parseFloat(e.split(", ")[t]),
    Qs = (e, t) => (n, {
        transform: r
    }) => {
        if (r === "none" || !r) return 0;
        const o = r.match(/^matrix3d\((.+)\)$/);
        if (o) return qs(o[1], t);
        {
            const s = r.match(/^matrix\((.+)\)$/);
            return s ? qs(s[1], e) : 0
        }
    },
    Nh = new Set(["x", "y", "z"]),
    Fh = sn.filter(e => !Nh.has(e));

function jh(e) {
    const t = [];
    return Fh.forEach(n => {
        const r = e.getValue(n);
        r !== void 0 && (t.push([n, r.get()]), r.set(n.startsWith("scale") ? 1 : 0))
    }), t.length && e.render(), t
}
const Dt = {
    width: ({
        x: e
    }, {
        paddingLeft: t = "0",
        paddingRight: n = "0"
    }) => e.max - e.min - parseFloat(t) - parseFloat(n),
    height: ({
        y: e
    }, {
        paddingTop: t = "0",
        paddingBottom: n = "0"
    }) => e.max - e.min - parseFloat(t) - parseFloat(n),
    top: (e, {
        top: t
    }) => parseFloat(t),
    left: (e, {
        left: t
    }) => parseFloat(t),
    bottom: ({
        y: e
    }, {
        top: t
    }) => parseFloat(t) + (e.max - e.min),
    right: ({
        x: e
    }, {
        left: t
    }) => parseFloat(t) + (e.max - e.min),
    x: Qs(4, 13),
    y: Qs(5, 14)
};
Dt.translateX = Dt.x;
Dt.translateY = Dt.y;
const Bh = (e, t, n) => {
        const r = t.measureViewportBox(),
            o = t.current,
            s = getComputedStyle(o),
            {
                display: i
            } = s,
            a = {};
        i === "none" && t.setStaticValue("display", e.display || "block"), n.forEach(l => {
            a[l] = Dt[l](r, s)
        }), t.render();
        const c = t.measureViewportBox();
        return n.forEach(l => {
            const f = t.getValue(l);
            f && f.jump(a[l]), e[l] = Dt[l](c, s)
        }), e
    },
    Uh = (e, t, n = {}, r = {}) => {
        t = {
            ...t
        }, r = {
            ...r
        };
        const o = Object.keys(t).filter(Qa);
        let s = [],
            i = !1;
        const a = [];
        if (o.forEach(c => {
                const l = e.getValue(c);
                if (!e.hasValue(c)) return;
                let f = n[c],
                    d = jt(f);
                const h = t[c];
                let p;
                if (_n(h)) {
                    const g = h.length,
                        m = h[0] === null ? 1 : 0;
                    f = h[m], d = jt(f);
                    for (let b = m; b < g && h[b] !== null; b++) p ? Ao(jt(h[b]) === p) : p = jt(h[b])
                } else p = jt(h);
                if (d !== p)
                    if (Zs(d) && Zs(p)) {
                        const g = l.get();
                        typeof g == "string" && l.set(parseFloat(g)), typeof h == "string" ? t[c] = parseFloat(h) : Array.isArray(h) && p === B && (t[c] = h.map(parseFloat))
                    } else d?.transform && p?.transform && (f === 0 || h === 0) ? f === 0 ? l.set(p.transform(f)) : t[c] = d.transform(h) : (i || (s = jh(e), i = !0), a.push(c), r[c] = r[c] !== void 0 ? r[c] : t[c], l.jump(h))
            }), a.length) {
            const c = a.indexOf("height") >= 0 ? window.pageYOffset : null,
                l = Bh(t, e, a);
            return s.length && s.forEach(([f, d]) => {
                e.getValue(f).set(d)
            }), e.render(), Xn && c !== null && window.scrollTo({
                top: c
            }), {
                target: l,
                transitionEnd: r
            }
        } else return {
            target: t,
            transitionEnd: r
        }
    };

function Wh(e, t, n, r) {
    return Vh(t) ? Uh(e, t, n, r) : {
        target: t,
        transitionEnd: r
    }
}
const Hh = (e, t, n, r) => {
        const o = Lh(e, t, r);
        return t = o.target, r = o.transitionEnd, Wh(e, t, n, r)
    },
    Qr = {
        current: null
    },
    Ja = {
        current: !1
    };

function Gh() {
    if (Ja.current = !0, !!Xn)
        if (window.matchMedia) {
            const e = window.matchMedia("(prefers-reduced-motion)"),
                t = () => Qr.current = e.matches;
            e.addListener(t), t()
        } else Qr.current = !1
}

function Kh(e, t, n) {
    const {
        willChange: r
    } = t;
    for (const o in t) {
        const s = t[o],
            i = n[o];
        if (fe(s)) e.addValue(o, s), Vn(r) && r.add(o);
        else if (fe(i)) e.addValue(o, Mt(s, {
            owner: e
        })), Vn(r) && r.remove(o);
        else if (i !== s)
            if (e.hasValue(o)) {
                const a = e.getValue(o);
                !a.hasAnimated && a.set(s)
            } else {
                const a = e.getStaticValue(o);
                e.addValue(o, Mt(a !== void 0 ? a : s, {
                    owner: e
                }))
            }
    }
    for (const o in n) t[o] === void 0 && e.removeValue(o);
    return t
}
const Js = new WeakMap,
    ec = Object.keys(qt),
    zh = ec.length,
    ei = ["AnimationStart", "AnimationComplete", "Update", "BeforeLayoutMeasure", "LayoutMeasure", "LayoutAnimationStart", "LayoutAnimationComplete"],
    Yh = ho.length;
class Xh {
    constructor({
        parent: t,
        props: n,
        presenceContext: r,
        reducedMotionConfig: o,
        visualState: s
    }, i = {}) {
        this.current = null, this.children = new Set, this.isVariantNode = !1, this.isControllingVariants = !1, this.shouldReduceMotion = null, this.values = new Map, this.features = {}, this.valueSubscriptions = new Map, this.prevMotionValues = {}, this.events = {}, this.propEventSubscriptions = {}, this.notifyUpdate = () => this.notify("Update", this.latestValues), this.render = () => {
            this.current && (this.triggerBuild(), this.renderInstance(this.current, this.renderState, this.props.style, this.projection))
        }, this.scheduleRender = () => Q.render(this.render, !1, !0);
        const {
            latestValues: a,
            renderState: c
        } = s;
        this.latestValues = a, this.baseTarget = {
            ...a
        }, this.initialValues = n.initial ? {
            ...a
        } : {}, this.renderState = c, this.parent = t, this.props = n, this.presenceContext = r, this.depth = t ? t.depth + 1 : 0, this.reducedMotionConfig = o, this.options = i, this.isControllingVariants = qn(n), this.isVariantNode = Fi(n), this.isVariantNode && (this.variantChildren = new Set), this.manuallyAnimateOnMount = !!(t && t.current);
        const {
            willChange: l,
            ...f
        } = this.scrapeMotionValuesFromProps(n, {});
        for (const d in f) {
            const h = f[d];
            a[d] !== void 0 && fe(h) && (h.set(a[d], !1), Vn(l) && l.add(d))
        }
    }
    scrapeMotionValuesFromProps(t, n) {
        return {}
    }
    mount(t) {
        this.current = t, Js.set(t, this), this.projection && !this.projection.instance && this.projection.mount(t), this.parent && this.isVariantNode && !this.isControllingVariants && (this.removeFromVariantTree = this.parent.addVariantChild(this)), this.values.forEach((n, r) => this.bindToMotionValue(r, n)), Ja.current || Gh(), this.shouldReduceMotion = this.reducedMotionConfig === "never" ? !1 : this.reducedMotionConfig === "always" ? !0 : Qr.current, this.parent && this.parent.children.add(this), this.update(this.props, this.presenceContext)
    }
    unmount() {
        Js.delete(this.current), this.projection && this.projection.unmount(), je(this.notifyUpdate), je(this.render), this.valueSubscriptions.forEach(t => t()), this.removeFromVariantTree && this.removeFromVariantTree(), this.parent && this.parent.children.delete(this);
        for (const t in this.events) this.events[t].clear();
        for (const t in this.features) this.features[t].unmount();
        this.current = null
    }
    bindToMotionValue(t, n) {
        const r = dt.has(t),
            o = n.on("change", i => {
                this.latestValues[t] = i, this.props.onUpdate && Q.update(this.notifyUpdate, !1, !0), r && this.projection && (this.projection.isTransformDirty = !0)
            }),
            s = n.on("renderRequest", this.scheduleRender);
        this.valueSubscriptions.set(t, () => {
            o(), s()
        })
    }
    sortNodePosition(t) {
        return !this.current || !this.sortInstanceNodePosition || this.type !== t.type ? 0 : this.sortInstanceNodePosition(this.current, t.current)
    }
    loadFeatures({
        children: t,
        ...n
    }, r, o, s) {
        let i, a;
        for (let c = 0; c < zh; c++) {
            const l = ec[c],
                {
                    isEnabled: f,
                    Feature: d,
                    ProjectionNode: h,
                    MeasureLayout: p
                } = qt[l];
            h && (i = h), f(n) && (!this.features[l] && d && (this.features[l] = new d(this)), p && (a = p))
        }
        if (!this.projection && i) {
            this.projection = new i(this.latestValues, this.parent && this.parent.projection);
            const {
                layoutId: c,
                layout: l,
                drag: f,
                dragConstraints: d,
                layoutScroll: h,
                layoutRoot: p
            } = n;
            this.projection.setOptions({
                layoutId: c,
                layout: l,
                alwaysMeasureLayout: !!f || d && Ct(d),
                visualElement: this,
                scheduleRender: () => this.scheduleRender(),
                animationType: typeof l == "string" ? l : "both",
                initialPromotionConfig: s,
                layoutScroll: h,
                layoutRoot: p
            })
        }
        return a
    }
    updateFeatures() {
        for (const t in this.features) {
            const n = this.features[t];
            n.isMounted ? n.update() : (n.mount(), n.isMounted = !0)
        }
    }
    triggerBuild() {
        this.build(this.renderState, this.latestValues, this.options, this.props)
    }
    measureViewportBox() {
        return this.current ? this.measureInstanceViewportBox(this.current, this.props) : ne()
    }
    getStaticValue(t) {
        return this.latestValues[t]
    }
    setStaticValue(t, n) {
        this.latestValues[t] = n
    }
    makeTargetAnimatable(t, n = !0) {
        return this.makeTargetAnimatableFromInstance(t, this.props, n)
    }
    update(t, n) {
        (t.transformTemplate || this.props.transformTemplate) && this.scheduleRender(), this.prevProps = this.props, this.props = t, this.prevPresenceContext = this.presenceContext, this.presenceContext = n;
        for (let r = 0; r < ei.length; r++) {
            const o = ei[r];
            this.propEventSubscriptions[o] && (this.propEventSubscriptions[o](), delete this.propEventSubscriptions[o]);
            const s = t["on" + o];
            s && (this.propEventSubscriptions[o] = this.on(o, s))
        }
        this.prevMotionValues = Kh(this, this.scrapeMotionValuesFromProps(t, this.prevProps), this.prevMotionValues), this.handleChildMotionValue && this.handleChildMotionValue()
    }
    getProps() {
        return this.props
    }
    getVariant(t) {
        return this.props.variants ? this.props.variants[t] : void 0
    }
    getDefaultTransition() {
        return this.props.transition
    }
    getTransformPagePoint() {
        return this.props.transformPagePoint
    }
    getClosestVariantNode() {
        return this.isVariantNode ? this : this.parent ? this.parent.getClosestVariantNode() : void 0
    }
    getVariantContext(t = !1) {
        if (t) return this.parent ? this.parent.getVariantContext() : void 0;
        if (!this.isControllingVariants) {
            const r = this.parent ? this.parent.getVariantContext() || {} : {};
            return this.props.initial !== void 0 && (r.initial = this.props.initial), r
        }
        const n = {};
        for (let r = 0; r < Yh; r++) {
            const o = ho[r],
                s = this.props[o];
            (Zt(s) || s === !1) && (n[o] = s)
        }
        return n
    }
    addVariantChild(t) {
        const n = this.getClosestVariantNode();
        if (n) return n.variantChildren && n.variantChildren.add(t), () => n.variantChildren.delete(t)
    }
    addValue(t, n) {
        n !== this.values.get(t) && (this.removeValue(t), this.bindToMotionValue(t, n)), this.values.set(t, n), this.latestValues[t] = n.get()
    }
    removeValue(t) {
        this.values.delete(t);
        const n = this.valueSubscriptions.get(t);
        n && (n(), this.valueSubscriptions.delete(t)), delete this.latestValues[t], this.removeValueFromRenderState(t, this.renderState)
    }
    hasValue(t) {
        return this.values.has(t)
    }
    getValue(t, n) {
        if (this.props.values && this.props.values[t]) return this.props.values[t];
        let r = this.values.get(t);
        return r === void 0 && n !== void 0 && (r = Mt(n, {
            owner: this
        }), this.addValue(t, r)), r
    }
    readValue(t) {
        var n;
        return this.latestValues[t] !== void 0 || !this.current ? this.latestValues[t] : (n = this.getBaseTargetFromProps(this.props, t)) !== null && n !== void 0 ? n : this.readValueFromInstance(this.current, t, this.options)
    }
    setBaseTarget(t, n) {
        this.baseTarget[t] = n
    }
    getBaseTarget(t) {
        var n;
        const {
            initial: r
        } = this.props, o = typeof r == "string" || typeof r == "object" ? (n = Co(this.props, r)) === null || n === void 0 ? void 0 : n[t] : void 0;
        if (r && o !== void 0) return o;
        const s = this.getBaseTargetFromProps(this.props, t);
        return s !== void 0 && !fe(s) ? s : this.initialValues[t] !== void 0 && o === void 0 ? void 0 : this.baseTarget[t]
    }
    on(t, n) {
        return this.events[t] || (this.events[t] = new Ro), this.events[t].add(n)
    }
    notify(t, ...n) {
        this.events[t] && this.events[t].notify(...n)
    }
}
class tc extends Xh {
    sortInstanceNodePosition(t, n) {
        return t.compareDocumentPosition(n) & 2 ? 1 : -1
    }
    getBaseTargetFromProps(t, n) {
        return t.style ? t.style[n] : void 0
    }
    removeValueFromRenderState(t, {
        vars: n,
        style: r
    }) {
        delete n[t], delete r[t]
    }
    makeTargetAnimatableFromInstance({
        transition: t,
        transitionEnd: n,
        ...r
    }, {
        transformValues: o
    }, s) {
        let i = pd(r, t || {}, this);
        if (o && (n && (n = o(n)), r && (r = o(r)), i && (i = o(i))), s) {
            dd(this, r, i);
            const a = Hh(this, r, i, n);
            n = a.transitionEnd, r = a.target
        }
        return {
            transition: t,
            transitionEnd: n,
            ...r
        }
    }
}

function Zh(e) {
    return window.getComputedStyle(e)
}
class qh extends tc {
    readValueFromInstance(t, n) {
        if (dt.has(n)) {
            const r = Eo(n);
            return r && r.default || 0
        } else {
            const r = Zh(t),
                o = (Wi(n) ? r.getPropertyValue(n) : r[n]) || 0;
            return typeof o == "string" ? o.trim() : o
        }
    }
    measureInstanceViewportBox(t, {
        transformPagePoint: n
    }) {
        return Wa(t, n)
    }
    build(t, n, r, o) {
        go(t, n, r, o.transformTemplate)
    }
    scrapeMotionValuesFromProps(t, n) {
        return wo(t, n)
    }
    handleChildMotionValue() {
        this.childSubscription && (this.childSubscription(), delete this.childSubscription);
        const {
            children: t
        } = this.props;
        fe(t) && (this.childSubscription = t.on("change", n => {
            this.current && (this.current.textContent = `${n}`)
        }))
    }
    renderInstance(t, n, r, o) {
        Xi(t, n, r, o)
    }
}
class Qh extends tc {
    constructor() {
        super(...arguments), this.isSVGTag = !1
    }
    getBaseTargetFromProps(t, n) {
        return t[n]
    }
    readValueFromInstance(t, n) {
        if (dt.has(n)) {
            const r = Eo(n);
            return r && r.default || 0
        }
        return n = Zi.has(n) ? n : xo(n), t.getAttribute(n)
    }
    measureInstanceViewportBox() {
        return ne()
    }
    scrapeMotionValuesFromProps(t, n) {
        return Qi(t, n)
    }
    build(t, n, r, o) {
        yo(t, n, r, this.isSVGTag, o.transformTemplate)
    }
    renderInstance(t, n, r, o) {
        qi(t, n, r, o)
    }
    mount(t) {
        this.isSVGTag = bo(t.tagName), super.mount(t)
    }
}
const Jh = (e, t) => mo(e) ? new Qh(t, {
        enableHardwareAcceleration: !1
    }) : new qh(t, {
        enableHardwareAcceleration: !0
    }),
    ep = {
        layout: {
            ProjectionNode: qa,
            MeasureLayout: Ha
        }
    },
    tp = {
        ...Td,
        ...Ku,
        ...Oh,
        ...ep
    },
    Jt = tu((e, t) => Du(e, t, tp, Jh));

function nc() {
    const e = u.useRef(!1);
    return Dn(() => (e.current = !0, () => {
        e.current = !1
    }), []), e
}

function np() {
    const e = nc(),
        [t, n] = u.useState(0),
        r = u.useCallback(() => {
            e.current && n(t + 1)
        }, [t]);
    return [u.useCallback(() => Q.postRender(r), [r]), t]
}
class rp extends u.Component {
    getSnapshotBeforeUpdate(t) {
        const n = this.props.childRef.current;
        if (n && t.isPresent && !this.props.isPresent) {
            const r = this.props.sizeRef.current;
            r.height = n.offsetHeight || 0, r.width = n.offsetWidth || 0, r.top = n.offsetTop, r.left = n.offsetLeft
        }
        return null
    }
    componentDidUpdate() {}
    render() {
        return this.props.children
    }
}

function op({
    children: e,
    isPresent: t
}) {
    const n = u.useId(),
        r = u.useRef(null),
        o = u.useRef({
            width: 0,
            height: 0,
            top: 0,
            left: 0
        });
    return u.useInsertionEffect(() => {
        const {
            width: s,
            height: i,
            top: a,
            left: c
        } = o.current;
        if (t || !r.current || !s || !i) return;
        r.current.dataset.motionPopId = n;
        const l = document.createElement("style");
        return document.head.appendChild(l), l.sheet && l.sheet.insertRule(`
          [data-motion-pop-id="${n}"] {
            position: absolute !important;
            width: ${s}px !important;
            height: ${i}px !important;
            top: ${a}px !important;
            left: ${c}px !important;
          }
        `), () => {
            document.head.removeChild(l)
        }
    }, [t]), u.createElement(rp, {
        isPresent: t,
        childRef: r,
        sizeRef: o
    }, u.cloneElement(e, {
        ref: r
    }))
}
const Tr = ({
    children: e,
    initial: t,
    isPresent: n,
    onExitComplete: r,
    custom: o,
    presenceAffectsLayout: s,
    mode: i
}) => {
    const a = Ji(sp),
        c = u.useId(),
        l = u.useMemo(() => ({
            id: c,
            initial: t,
            isPresent: n,
            custom: o,
            onExitComplete: f => {
                a.set(f, !0);
                for (const d of a.values())
                    if (!d) return;
                r && r()
            },
            register: f => (a.set(f, !1), () => a.delete(f))
        }), s ? void 0 : [n]);
    return u.useMemo(() => {
        a.forEach((f, d) => a.set(d, !1))
    }, [n]), u.useEffect(() => {
        !n && !a.size && r && r()
    }, [n]), i === "popLayout" && (e = u.createElement(op, {
        isPresent: n
    }, e)), u.createElement(Yn.Provider, {
        value: l
    }, e)
};

function sp() {
    return new Map
}

function ip(e) {
    return u.useEffect(() => () => e(), [])
}
const wt = e => e.key || "";

function ap(e, t) {
    e.forEach(n => {
        const r = wt(n);
        t.set(r, n)
    })
}

function cp(e) {
    const t = [];
    return u.Children.forEach(e, n => {
        u.isValidElement(n) && t.push(n)
    }), t
}
const Oo = ({
    children: e,
    custom: t,
    initial: n = !0,
    onExitComplete: r,
    exitBeforeEnter: o,
    presenceAffectsLayout: s = !0,
    mode: i = "sync"
}) => {
    const a = u.useContext(po).forceRender || np()[0],
        c = nc(),
        l = cp(e);
    let f = l;
    const d = u.useRef(new Map).current,
        h = u.useRef(f),
        p = u.useRef(new Map).current,
        g = u.useRef(!0);
    if (Dn(() => {
            g.current = !1, ap(l, p), h.current = f
        }), ip(() => {
            g.current = !0, p.clear(), d.clear()
        }), g.current) return u.createElement(u.Fragment, null, f.map(v => u.createElement(Tr, {
        key: wt(v),
        isPresent: !0,
        initial: n ? void 0 : !1,
        presenceAffectsLayout: s,
        mode: i
    }, v)));
    f = [...f];
    const m = h.current.map(wt),
        b = l.map(wt),
        x = m.length;
    for (let v = 0; v < x; v++) {
        const y = m[v];
        b.indexOf(y) === -1 && !d.has(y) && d.set(y, void 0)
    }
    return i === "wait" && d.size && (f = []), d.forEach((v, y) => {
        if (b.indexOf(y) !== -1) return;
        const C = p.get(y);
        if (!C) return;
        const $ = m.indexOf(y);
        let A = v;
        if (!A) {
            const w = () => {
                p.delete(y), d.delete(y);
                const S = h.current.findIndex(E => E.key === y);
                if (h.current.splice(S, 1), !d.size) {
                    if (h.current = l, c.current === !1) return;
                    a(), r && r()
                }
            };
            A = u.createElement(Tr, {
                key: wt(C),
                isPresent: !1,
                onExitComplete: w,
                custom: t,
                presenceAffectsLayout: s,
                mode: i
            }, C), d.set(y, A)
        }
        f.splice($, 0, A)
    }), f = f.map(v => {
        const y = v.key;
        return d.has(y) ? v : u.createElement(Tr, {
            key: wt(v),
            isPresent: !0,
            presenceAffectsLayout: s,
            mode: i
        }, v)
    }), u.createElement(u.Fragment, null, d.size ? f : f.map(v => u.cloneElement(v)))
};

function ot(e, t) {
    if (e == null) return {};
    var n = {},
        r = Object.keys(e),
        o, s;
    for (s = 0; s < r.length; s++) o = r[s], !(t.indexOf(o) >= 0) && (n[o] = e[o]);
    return n
}
var lp = ["color"],
    up = u.forwardRef(function(e, t) {
        var n = e.color,
            r = n === void 0 ? "currentColor" : n,
            o = ot(e, lp);
        return u.createElement("svg", Object.assign({
            width: "15",
            height: "15",
            viewBox: "0 0 15 15",
            fill: "none",
            xmlns: "http://www.w3.org/2000/svg"
        }, o, {
            ref: t
        }), u.createElement("path", {
            d: "M4.18179 6.18181C4.35753 6.00608 4.64245 6.00608 4.81819 6.18181L7.49999 8.86362L10.1818 6.18181C10.3575 6.00608 10.6424 6.00608 10.8182 6.18181C10.9939 6.35755 10.9939 6.64247 10.8182 6.81821L7.81819 9.81821C7.73379 9.9026 7.61934 9.95001 7.49999 9.95001C7.38064 9.95001 7.26618 9.9026 7.18179 9.81821L4.18179 6.81821C4.00605 6.64247 4.00605 6.35755 4.18179 6.18181Z",
            fill: r,
            fillRule: "evenodd",
            clipRule: "evenodd"
        }))
    }),
    fp = ["color"],
    dp = u.forwardRef(function(e, t) {
        var n = e.color,
            r = n === void 0 ? "currentColor" : n,
            o = ot(e, fp);
        return u.createElement("svg", Object.assign({
            width: "15",
            height: "15",
            viewBox: "0 0 15 15",
            fill: "none",
            xmlns: "http://www.w3.org/2000/svg"
        }, o, {
            ref: t
        }), u.createElement("path", {
            d: "M11.4669 3.72684C11.7558 3.91574 11.8369 4.30308 11.648 4.59198L7.39799 11.092C7.29783 11.2452 7.13556 11.3467 6.95402 11.3699C6.77247 11.3931 6.58989 11.3355 6.45446 11.2124L3.70446 8.71241C3.44905 8.48022 3.43023 8.08494 3.66242 7.82953C3.89461 7.57412 4.28989 7.55529 4.5453 7.78749L6.75292 9.79441L10.6018 3.90792C10.7907 3.61902 11.178 3.53795 11.4669 3.72684Z",
            fill: r,
            fillRule: "evenodd",
            clipRule: "evenodd"
        }))
    }),
    hp = ["color"],
    pp = u.forwardRef(function(e, t) {
        var n = e.color,
            r = n === void 0 ? "currentColor" : n,
            o = ot(e, hp);
        return u.createElement("svg", Object.assign({
            width: "15",
            height: "15",
            viewBox: "0 0 15 15",
            fill: "none",
            xmlns: "http://www.w3.org/2000/svg"
        }, o, {
            ref: t
        }), u.createElement("path", {
            d: "M6.1584 3.13508C6.35985 2.94621 6.67627 2.95642 6.86514 3.15788L10.6151 7.15788C10.7954 7.3502 10.7954 7.64949 10.6151 7.84182L6.86514 11.8418C6.67627 12.0433 6.35985 12.0535 6.1584 11.8646C5.95694 11.6757 5.94673 11.3593 6.1356 11.1579L9.565 7.49985L6.1356 3.84182C5.94673 3.64036 5.95694 3.32394 6.1584 3.13508Z",
            fill: r,
            fillRule: "evenodd",
            clipRule: "evenodd"
        }))
    }),
    mp = ["color"],
    gp = u.forwardRef(function(e, t) {
        var n = e.color,
            r = n === void 0 ? "currentColor" : n,
            o = ot(e, mp);
        return u.createElement("svg", Object.assign({
            width: "15",
            height: "15",
            viewBox: "0 0 15 15",
            fill: "none",
            xmlns: "http://www.w3.org/2000/svg"
        }, o, {
            ref: t
        }), u.createElement("path", {
            d: "M11.7816 4.03157C12.0062 3.80702 12.0062 3.44295 11.7816 3.2184C11.5571 2.99385 11.193 2.99385 10.9685 3.2184L7.50005 6.68682L4.03164 3.2184C3.80708 2.99385 3.44301 2.99385 3.21846 3.2184C2.99391 3.44295 2.99391 3.80702 3.21846 4.03157L6.68688 7.49999L3.21846 10.9684C2.99391 11.193 2.99391 11.557 3.21846 11.7816C3.44301 12.0061 3.80708 12.0061 4.03164 11.7816L7.50005 8.31316L10.9685 11.7816C11.193 12.0061 11.5571 12.0061 11.7816 11.7816C12.0062 11.557 12.0062 11.193 11.7816 10.9684L8.31322 7.49999L11.7816 4.03157Z",
            fill: r,
            fillRule: "evenodd",
            clipRule: "evenodd"
        }))
    }),
    vp = ["color"],
    yp = u.forwardRef(function(e, t) {
        var n = e.color,
            r = n === void 0 ? "currentColor" : n,
            o = ot(e, vp);
        return u.createElement("svg", Object.assign({
            width: "15",
            height: "15",
            viewBox: "0 0 15 15",
            fill: "none",
            xmlns: "http://www.w3.org/2000/svg"
        }, o, {
            ref: t
        }), u.createElement("path", {
            d: "M1 3.25C1 3.11193 1.11193 3 1.25 3H13.75C13.8881 3 14 3.11193 14 3.25V10.75C14 10.8881 13.8881 11 13.75 11H1.25C1.11193 11 1 10.8881 1 10.75V3.25ZM1.25 2C0.559643 2 0 2.55964 0 3.25V10.75C0 11.4404 0.559644 12 1.25 12H5.07341L4.82991 13.2986C4.76645 13.6371 5.02612 13.95 5.37049 13.95H9.62951C9.97389 13.95 10.2336 13.6371 10.1701 13.2986L9.92659 12H13.75C14.4404 12 15 11.4404 15 10.75V3.25C15 2.55964 14.4404 2 13.75 2H1.25ZM9.01091 12H5.98909L5.79222 13.05H9.20778L9.01091 12Z",
            fill: r,
            fillRule: "evenodd",
            clipRule: "evenodd"
        }))
    }),
    bp = ["color"],
    xp = u.forwardRef(function(e, t) {
        var n = e.color,
            r = n === void 0 ? "currentColor" : n,
            o = ot(e, bp);
        return u.createElement("svg", Object.assign({
            width: "15",
            height: "15",
            viewBox: "0 0 15 15",
            fill: "none",
            xmlns: "http://www.w3.org/2000/svg"
        }, o, {
            ref: t
        }), u.createElement("path", {
            d: "M9.875 7.5C9.875 8.81168 8.81168 9.875 7.5 9.875C6.18832 9.875 5.125 8.81168 5.125 7.5C5.125 6.18832 6.18832 5.125 7.5 5.125C8.81168 5.125 9.875 6.18832 9.875 7.5Z",
            fill: r
        }))
    }),
    wp = ["color"],
    Cp = u.forwardRef(function(e, t) {
        var n = e.color,
            r = n === void 0 ? "currentColor" : n,
            o = ot(e, wp);
        return u.createElement("svg", Object.assign({
            width: "15",
            height: "15",
            viewBox: "0 0 15 15",
            fill: "none",
            xmlns: "http://www.w3.org/2000/svg"
        }, o, {
            ref: t
        }), u.createElement("path", {
            d: "M1.49988 2.00012C1.77602 2.00012 1.99988 1.77626 1.99988 1.50012C1.99988 1.22398 1.77602 1.00012 1.49988 1.00012C1.22374 1.00012 0.999878 1.22398 0.999878 1.50012C0.999878 1.77626 1.22374 2.00012 1.49988 2.00012ZM4.49988 2.00012C4.77602 2.00012 4.99988 1.77626 4.99988 1.50012C4.99988 1.22398 4.77602 1.00012 4.49988 1.00012C4.22374 1.00012 3.99988 1.22398 3.99988 1.50012C3.99988 1.77626 4.22374 2.00012 4.49988 2.00012ZM7.99988 1.50012C7.99988 1.77626 7.77602 2.00012 7.49988 2.00012C7.22374 2.00012 6.99988 1.77626 6.99988 1.50012C6.99988 1.22398 7.22374 1.00012 7.49988 1.00012C7.77602 1.00012 7.99988 1.22398 7.99988 1.50012ZM10.4999 2.00012C10.776 2.00012 10.9999 1.77626 10.9999 1.50012C10.9999 1.22398 10.776 1.00012 10.4999 1.00012C10.2237 1.00012 9.99988 1.22398 9.99988 1.50012C9.99988 1.77626 10.2237 2.00012 10.4999 2.00012ZM13.9999 1.50012C13.9999 1.77626 13.776 2.00012 13.4999 2.00012C13.2237 2.00012 12.9999 1.77626 12.9999 1.50012C12.9999 1.22398 13.2237 1.00012 13.4999 1.00012C13.776 1.00012 13.9999 1.22398 13.9999 1.50012ZM1.49988 14.0001C1.77602 14.0001 1.99988 13.7763 1.99988 13.5001C1.99988 13.224 1.77602 13.0001 1.49988 13.0001C1.22374 13.0001 0.999878 13.224 0.999878 13.5001C0.999878 13.7763 1.22374 14.0001 1.49988 14.0001ZM1.99988 10.5001C1.99988 10.7763 1.77602 11.0001 1.49988 11.0001C1.22374 11.0001 0.999878 10.7763 0.999878 10.5001C0.999878 10.224 1.22374 10.0001 1.49988 10.0001C1.77602 10.0001 1.99988 10.224 1.99988 10.5001ZM1.49988 8.00012C1.77602 8.00012 1.99988 7.77626 1.99988 7.50012C1.99988 7.22398 1.77602 7.00012 1.49988 7.00012C1.22374 7.00012 0.999878 7.22398 0.999878 7.50012C0.999878 7.77626 1.22374 8.00012 1.49988 8.00012ZM1.99988 4.50012C1.99988 4.77626 1.77602 5.00012 1.49988 5.00012C1.22374 5.00012 0.999878 4.77626 0.999878 4.50012C0.999878 4.22398 1.22374 4.00012 1.49988 4.00012C1.77602 4.00012 1.99988 4.22398 1.99988 4.50012ZM13.4999 11.0001C13.776 11.0001 13.9999 10.7763 13.9999 10.5001C13.9999 10.224 13.776 10.0001 13.4999 10.0001C13.2237 10.0001 12.9999 10.224 12.9999 10.5001C12.9999 10.7763 13.2237 11.0001 13.4999 11.0001ZM13.9999 7.50012C13.9999 7.77626 13.776 8.00012 13.4999 8.00012C13.2237 8.00012 12.9999 7.77626 12.9999 7.50012C12.9999 7.22398 13.2237 7.00012 13.4999 7.00012C13.776 7.00012 13.9999 7.22398 13.9999 7.50012ZM13.4999 5.00012C13.776 5.00012 13.9999 4.77626 13.9999 4.50012C13.9999 4.22398 13.776 4.00012 13.4999 4.00012C13.2237 4.00012 12.9999 4.22398 12.9999 4.50012C12.9999 4.77626 13.2237 5.00012 13.4999 5.00012ZM4.99988 13.5001C4.99988 13.7763 4.77602 14.0001 4.49988 14.0001C4.22374 14.0001 3.99988 13.7763 3.99988 13.5001C3.99988 13.224 4.22374 13.0001 4.49988 13.0001C4.77602 13.0001 4.99988 13.224 4.99988 13.5001ZM7.49988 14.0001C7.77602 14.0001 7.99988 13.7763 7.99988 13.5001C7.99988 13.224 7.77602 13.0001 7.49988 13.0001C7.22374 13.0001 6.99988 13.224 6.99988 13.5001C6.99988 13.7763 7.22374 14.0001 7.49988 14.0001ZM10.9999 13.5001C10.9999 13.7763 10.776 14.0001 10.4999 14.0001C10.2237 14.0001 9.99988 13.7763 9.99988 13.5001C9.99988 13.224 10.2237 13.0001 10.4999 13.0001C10.776 13.0001 10.9999 13.224 10.9999 13.5001ZM13.4999 14.0001C13.776 14.0001 13.9999 13.7763 13.9999 13.5001C13.9999 13.224 13.776 13.0001 13.4999 13.0001C13.2237 13.0001 12.9999 13.224 12.9999 13.5001C12.9999 13.7763 13.2237 14.0001 13.4999 14.0001ZM3.99988 5.00012C3.99988 4.44784 4.44759 4.00012 4.99988 4.00012H9.99988C10.5522 4.00012 10.9999 4.44784 10.9999 5.00012V10.0001C10.9999 10.5524 10.5522 11.0001 9.99988 11.0001H4.99988C4.44759 11.0001 3.99988 10.5524 3.99988 10.0001V5.00012ZM4.99988 5.00012H9.99988V10.0001H4.99988V5.00012Z",
            fill: r,
            fillRule: "evenodd",
            clipRule: "evenodd"
        }))
    }),
    Ap = ["color"],
    $p = u.forwardRef(function(e, t) {
        var n = e.color,
            r = n === void 0 ? "currentColor" : n,
            o = ot(e, Ap);
        return u.createElement("svg", Object.assign({
            width: "15",
            height: "15",
            viewBox: "0 0 15 15",
            fill: "none",
            xmlns: "http://www.w3.org/2000/svg"
        }, o, {
            ref: t
        }), u.createElement("path", {
            d: "M2.85714 2H12.1429C12.6162 2 13 2.38376 13 2.85714V12.1429C13 12.6162 12.6162 13 12.1429 13H2.85714C2.38376 13 2 12.6162 2 12.1429V2.85714C2 2.38376 2.38376 2 2.85714 2ZM1 2.85714C1 1.83147 1.83147 1 2.85714 1H12.1429C13.1685 1 14 1.83147 14 2.85714V12.1429C14 13.1685 13.1685 14 12.1429 14H2.85714C1.83147 14 1 13.1685 1 12.1429V2.85714ZM7.49988 5.00012C7.77602 5.00012 7.99988 4.77626 7.99988 4.50012C7.99988 4.22398 7.77602 4.00012 7.49988 4.00012C7.22374 4.00012 6.99988 4.22398 6.99988 4.50012C6.99988 4.77626 7.22374 5.00012 7.49988 5.00012ZM4.49988 11.0001C4.77602 11.0001 4.99988 10.7763 4.99988 10.5001C4.99988 10.224 4.77602 10.0001 4.49988 10.0001C4.22374 10.0001 3.99988 10.224 3.99988 10.5001C3.99988 10.7763 4.22374 11.0001 4.49988 11.0001ZM4.99988 7.50012C4.99988 7.77626 4.77602 8.00012 4.49988 8.00012C4.22374 8.00012 3.99988 7.77626 3.99988 7.50012C3.99988 7.22398 4.22374 7.00012 4.49988 7.00012C4.77602 7.00012 4.99988 7.22398 4.99988 7.50012ZM4.49988 5.00012C4.77602 5.00012 4.99988 4.77626 4.99988 4.50012C4.99988 4.22398 4.77602 4.00012 4.49988 4.00012C4.22374 4.00012 3.99988 4.22398 3.99988 4.50012C3.99988 4.77626 4.22374 5.00012 4.49988 5.00012ZM10.9999 10.5001C10.9999 10.7763 10.776 11.0001 10.4999 11.0001C10.2237 11.0001 9.99988 10.7763 9.99988 10.5001C9.99988 10.224 10.2237 10.0001 10.4999 10.0001C10.776 10.0001 10.9999 10.224 10.9999 10.5001ZM10.4999 8.00012C10.776 8.00012 10.9999 7.77626 10.9999 7.50012C10.9999 7.22398 10.776 7.00012 10.4999 7.00012C10.2237 7.00012 9.99988 7.22398 9.99988 7.50012C9.99988 7.77626 10.2237 8.00012 10.4999 8.00012ZM10.9999 4.50012C10.9999 4.77626 10.776 5.00012 10.4999 5.00012C10.2237 5.00012 9.99988 4.77626 9.99988 4.50012C9.99988 4.22398 10.2237 4.00012 10.4999 4.00012C10.776 4.00012 10.9999 4.22398 10.9999 4.50012ZM7.49988 11.0001C7.77602 11.0001 7.99988 10.7763 7.99988 10.5001C7.99988 10.224 7.77602 10.0001 7.49988 10.0001C7.22374 10.0001 6.99988 10.224 6.99988 10.5001C6.99988 10.7763 7.22374 11.0001 7.49988 11.0001Z",
            fill: r,
            fillRule: "evenodd",
            clipRule: "evenodd"
        }))
    }),
    tr = {
        exports: {}
    },
    rc = {},
    oc = {
        exports: {}
    },
    Pp = "SECRET_DO_NOT_PASS_THIS_OR_YOU_WILL_BE_FIRED",
    Sp = Pp,
    Ep = Sp;

function sc() {}

function ic() {}
ic.resetWarningCache = sc;
var Tp = function() {
    function e(r, o, s, i, a, c) {
        if (c !== Ep) {
            var l = new Error("Calling PropTypes validators directly is not supported by the `prop-types` package. Use PropTypes.checkPropTypes() to call them. Read more at http://fb.me/use-check-prop-types");
            throw l.name = "Invariant Violation", l
        }
    }
    e.isRequired = e;

    function t() {
        return e
    }
    var n = {
        array: e,
        bigint: e,
        bool: e,
        func: e,
        number: e,
        object: e,
        string: e,
        symbol: e,
        any: e,
        arrayOf: t,
        element: e,
        elementType: e,
        instanceOf: t,
        node: e,
        objectOf: t,
        oneOf: t,
        oneOfType: t,
        shape: t,
        exact: t,
        checkPropTypes: ic,
        resetWarningCache: sc
    };
    return n.PropTypes = n, n
};
oc.exports = Tp();
var ac = oc.exports;

function cc(e) {
    var t, n, r = "";
    if (typeof e == "string" || typeof e == "number") r += e;
    else if (typeof e == "object")
        if (Array.isArray(e))
            for (t = 0; t < e.length; t++) e[t] && (n = cc(e[t])) && (r && (r += " "), r += n);
        else
            for (t in e) e[t] && (r && (r += " "), r += t);
    return r
}

function ti() {
    for (var e, t, n = 0, r = ""; n < arguments.length;)(e = arguments[n++]) && (t = cc(e)) && (r && (r += " "), r += t);
    return r
}
const Mp = Object.freeze(Object.defineProperty({
        __proto__: null,
        clsx: ti,
        default: ti
    }, Symbol.toStringTag, {
        value: "Module"
    })),
    Dp = Vl(Mp);
var te = {},
    Le = {};
Object.defineProperty(Le, "__esModule", {
    value: !0
});
Le.dontSetMe = Lp;
Le.findInArray = Rp;
Le.int = kp;
Le.isFunction = Op;
Le.isNum = _p;

function Rp(e, t) {
    for (var n = 0, r = e.length; n < r; n++)
        if (t.apply(t, [e[n], n, e])) return e[n]
}

function Op(e) {
    return typeof e == "function" || Object.prototype.toString.call(e) === "[object Function]"
}

function _p(e) {
    return typeof e == "number" && !isNaN(e)
}

function kp(e) {
    return parseInt(e, 10)
}

function Lp(e, t, n) {
    if (e[t]) return new Error("Invalid prop ".concat(t, " passed to ").concat(n, " - do not set this, set it on the child."))
}
var pt = {};
Object.defineProperty(pt, "__esModule", {
    value: !0
});
pt.browserPrefixToKey = uc;
pt.browserPrefixToStyle = Ip;
pt.default = void 0;
pt.getPrefix = lc;
var Mr = ["Moz", "Webkit", "O", "ms"];

function lc() {
    var e, t, n = arguments.length > 0 && arguments[0] !== void 0 ? arguments[0] : "transform";
    if (typeof window > "u") return "";
    var r = (e = window.document) === null || e === void 0 || (t = e.documentElement) === null || t === void 0 ? void 0 : t.style;
    if (!r || n in r) return "";
    for (var o = 0; o < Mr.length; o++)
        if (uc(n, Mr[o]) in r) return Mr[o];
    return ""
}

function uc(e, t) {
    return t ? "".concat(t).concat(Vp(e)) : e
}

function Ip(e, t) {
    return t ? "-".concat(t.toLowerCase(), "-").concat(e) : e
}

function Vp(e) {
    for (var t = "", n = !0, r = 0; r < e.length; r++) n ? (t += e[r].toUpperCase(), n = !1) : e[r] === "-" ? n = !0 : t += e[r];
    return t
}
var Np = lc();
pt.default = Np;

function Jr(e) {
    "@babel/helpers - typeof";
    return Jr = typeof Symbol == "function" && typeof Symbol.iterator == "symbol" ? function(t) {
        return typeof t
    } : function(t) {
        return t && typeof Symbol == "function" && t.constructor === Symbol && t !== Symbol.prototype ? "symbol" : typeof t
    }, Jr(e)
}
Object.defineProperty(te, "__esModule", {
    value: !0
});
te.addClassName = mc;
te.addEvent = Bp;
te.addUserSelectStyles = Qp;
te.createCSSTransform = Yp;
te.createSVGTransform = Xp;
te.getTouch = Zp;
te.getTouchIdentifier = qp;
te.getTranslation = _o;
te.innerHeight = Gp;
te.innerWidth = Kp;
te.matchesSelector = pc;
te.matchesSelectorAndParentsTo = jp;
te.offsetXYFromParent = zp;
te.outerHeight = Wp;
te.outerWidth = Hp;
te.removeClassName = gc;
te.removeEvent = Up;
te.removeUserSelectStyles = Jp;
var ve = Le,
    ni = Fp(pt);

function fc(e) {
    if (typeof WeakMap != "function") return null;
    var t = new WeakMap,
        n = new WeakMap;
    return (fc = function(o) {
        return o ? n : t
    })(e)
}

function Fp(e, t) {
    if (!t && e && e.__esModule) return e;
    if (e === null || Jr(e) !== "object" && typeof e != "function") return {
        default: e
    };
    var n = fc(t);
    if (n && n.has(e)) return n.get(e);
    var r = {},
        o = Object.defineProperty && Object.getOwnPropertyDescriptor;
    for (var s in e)
        if (s !== "default" && Object.prototype.hasOwnProperty.call(e, s)) {
            var i = o ? Object.getOwnPropertyDescriptor(e, s) : null;
            i && (i.get || i.set) ? Object.defineProperty(r, s, i) : r[s] = e[s]
        } return r.default = e, n && n.set(e, r), r
}

function ri(e, t) {
    var n = Object.keys(e);
    if (Object.getOwnPropertySymbols) {
        var r = Object.getOwnPropertySymbols(e);
        t && (r = r.filter(function(o) {
            return Object.getOwnPropertyDescriptor(e, o).enumerable
        })), n.push.apply(n, r)
    }
    return n
}

function dc(e) {
    for (var t = 1; t < arguments.length; t++) {
        var n = arguments[t] != null ? arguments[t] : {};
        t % 2 ? ri(Object(n), !0).forEach(function(r) {
            hc(e, r, n[r])
        }) : Object.getOwnPropertyDescriptors ? Object.defineProperties(e, Object.getOwnPropertyDescriptors(n)) : ri(Object(n)).forEach(function(r) {
            Object.defineProperty(e, r, Object.getOwnPropertyDescriptor(n, r))
        })
    }
    return e
}

function hc(e, t, n) {
    return t in e ? Object.defineProperty(e, t, {
        value: n,
        enumerable: !0,
        configurable: !0,
        writable: !0
    }) : e[t] = n, e
}
var vn = "";

function pc(e, t) {
    return vn || (vn = (0, ve.findInArray)(["matches", "webkitMatchesSelector", "mozMatchesSelector", "msMatchesSelector", "oMatchesSelector"], function(n) {
        return (0, ve.isFunction)(e[n])
    })), (0, ve.isFunction)(e[vn]) ? e[vn](t) : !1
}

function jp(e, t, n) {
    var r = e;
    do {
        if (pc(r, t)) return !0;
        if (r === n) return !1;
        r = r.parentNode
    } while (r);
    return !1
}

function Bp(e, t, n, r) {
    if (e) {
        var o = dc({
            capture: !0
        }, r);
        e.addEventListener ? e.addEventListener(t, n, o) : e.attachEvent ? e.attachEvent("on" + t, n) : e["on" + t] = n
    }
}

function Up(e, t, n, r) {
    if (e) {
        var o = dc({
            capture: !0
        }, r);
        e.removeEventListener ? e.removeEventListener(t, n, o) : e.detachEvent ? e.detachEvent("on" + t, n) : e["on" + t] = null
    }
}

function Wp(e) {
    var t = e.clientHeight,
        n = e.ownerDocument.defaultView.getComputedStyle(e);
    return t += (0, ve.int)(n.borderTopWidth), t += (0, ve.int)(n.borderBottomWidth), t
}

function Hp(e) {
    var t = e.clientWidth,
        n = e.ownerDocument.defaultView.getComputedStyle(e);
    return t += (0, ve.int)(n.borderLeftWidth), t += (0, ve.int)(n.borderRightWidth), t
}

function Gp(e) {
    var t = e.clientHeight,
        n = e.ownerDocument.defaultView.getComputedStyle(e);
    return t -= (0, ve.int)(n.paddingTop), t -= (0, ve.int)(n.paddingBottom), t
}

function Kp(e) {
    var t = e.clientWidth,
        n = e.ownerDocument.defaultView.getComputedStyle(e);
    return t -= (0, ve.int)(n.paddingLeft), t -= (0, ve.int)(n.paddingRight), t
}

function zp(e, t, n) {
    var r = t === t.ownerDocument.body,
        o = r ? {
            left: 0,
            top: 0
        } : t.getBoundingClientRect(),
        s = (e.clientX + t.scrollLeft - o.left) / n,
        i = (e.clientY + t.scrollTop - o.top) / n;
    return {
        x: s,
        y: i
    }
}

function Yp(e, t) {
    var n = _o(e, t, "px");
    return hc({}, (0, ni.browserPrefixToKey)("transform", ni.default), n)
}

function Xp(e, t) {
    var n = _o(e, t, "");
    return n
}

function _o(e, t, n) {
    var r = e.x,
        o = e.y,
        s = "translate(".concat(r).concat(n, ",").concat(o).concat(n, ")");
    if (t) {
        var i = "".concat(typeof t.x == "string" ? t.x : t.x + n),
            a = "".concat(typeof t.y == "string" ? t.y : t.y + n);
        s = "translate(".concat(i, ", ").concat(a, ")") + s
    }
    return s
}

function Zp(e, t) {
    return e.targetTouches && (0, ve.findInArray)(e.targetTouches, function(n) {
        return t === n.identifier
    }) || e.changedTouches && (0, ve.findInArray)(e.changedTouches, function(n) {
        return t === n.identifier
    })
}

function qp(e) {
    if (e.targetTouches && e.targetTouches[0]) return e.targetTouches[0].identifier;
    if (e.changedTouches && e.changedTouches[0]) return e.changedTouches[0].identifier
}

function Qp(e) {
    if (e) {
        var t = e.getElementById("react-draggable-style-el");
        t || (t = e.createElement("style"), t.type = "text/css", t.id = "react-draggable-style-el", t.innerHTML = `.react-draggable-transparent-selection *::-moz-selection {all: inherit;}
`, t.innerHTML += `.react-draggable-transparent-selection *::selection {all: inherit;}
`, e.getElementsByTagName("head")[0].appendChild(t)), e.body && mc(e.body, "react-draggable-transparent-selection")
    }
}

function Jp(e) {
    if (e) try {
        if (e.body && gc(e.body, "react-draggable-transparent-selection"), e.selection) e.selection.empty();
        else {
            var t = (e.defaultView || window).getSelection();
            t && t.type !== "Caret" && t.removeAllRanges()
        }
    } catch {}
}

function mc(e, t) {
    e.classList ? e.classList.add(t) : e.className.match(new RegExp("(?:^|\\s)".concat(t, "(?!\\S)"))) || (e.className += " ".concat(t))
}

function gc(e, t) {
    e.classList ? e.classList.remove(t) : e.className = e.className.replace(new RegExp("(?:^|\\s)".concat(t, "(?!\\S)"), "g"), "")
}
var Ie = {};
Object.defineProperty(Ie, "__esModule", {
    value: !0
});
Ie.canDragX = nm;
Ie.canDragY = rm;
Ie.createCoreData = sm;
Ie.createDraggableData = im;
Ie.getBoundPosition = em;
Ie.getControlPosition = om;
Ie.snapToGrid = tm;
var pe = Le,
    St = te;

function em(e, t, n) {
    if (!e.props.bounds) return [t, n];
    var r = e.props.bounds;
    r = typeof r == "string" ? r : am(r);
    var o = ko(e);
    if (typeof r == "string") {
        var s = o.ownerDocument,
            i = s.defaultView,
            a;
        if (r === "parent" ? a = o.parentNode : a = s.querySelector(r), !(a instanceof i.HTMLElement)) throw new Error('Bounds selector "' + r + '" could not find an element.');
        var c = a,
            l = i.getComputedStyle(o),
            f = i.getComputedStyle(c);
        r = {
            left: -o.offsetLeft + (0, pe.int)(f.paddingLeft) + (0, pe.int)(l.marginLeft),
            top: -o.offsetTop + (0, pe.int)(f.paddingTop) + (0, pe.int)(l.marginTop),
            right: (0, St.innerWidth)(c) - (0, St.outerWidth)(o) - o.offsetLeft + (0, pe.int)(f.paddingRight) - (0, pe.int)(l.marginRight),
            bottom: (0, St.innerHeight)(c) - (0, St.outerHeight)(o) - o.offsetTop + (0, pe.int)(f.paddingBottom) - (0, pe.int)(l.marginBottom)
        }
    }
    return (0, pe.isNum)(r.right) && (t = Math.min(t, r.right)), (0, pe.isNum)(r.bottom) && (n = Math.min(n, r.bottom)), (0, pe.isNum)(r.left) && (t = Math.max(t, r.left)), (0, pe.isNum)(r.top) && (n = Math.max(n, r.top)), [t, n]
}

function tm(e, t, n) {
    var r = Math.round(t / e[0]) * e[0],
        o = Math.round(n / e[1]) * e[1];
    return [r, o]
}

function nm(e) {
    return e.props.axis === "both" || e.props.axis === "x"
}

function rm(e) {
    return e.props.axis === "both" || e.props.axis === "y"
}

function om(e, t, n) {
    var r = typeof t == "number" ? (0, St.getTouch)(e, t) : null;
    if (typeof t == "number" && !r) return null;
    var o = ko(n),
        s = n.props.offsetParent || o.offsetParent || o.ownerDocument.body;
    return (0, St.offsetXYFromParent)(r || e, s, n.props.scale)
}

function sm(e, t, n) {
    var r = e.state,
        o = !(0, pe.isNum)(r.lastX),
        s = ko(e);
    return o ? {
        node: s,
        deltaX: 0,
        deltaY: 0,
        lastX: t,
        lastY: n,
        x: t,
        y: n
    } : {
        node: s,
        deltaX: t - r.lastX,
        deltaY: n - r.lastY,
        lastX: r.lastX,
        lastY: r.lastY,
        x: t,
        y: n
    }
}

function im(e, t) {
    var n = e.props.scale;
    return {
        node: t.node,
        x: e.state.x + t.deltaX / n,
        y: e.state.y + t.deltaY / n,
        deltaX: t.deltaX / n,
        deltaY: t.deltaY / n,
        lastX: e.state.x,
        lastY: e.state.y
    }
}

function am(e) {
    return {
        left: e.left,
        top: e.top,
        right: e.right,
        bottom: e.bottom
    }
}

function ko(e) {
    var t = e.findDOMNode();
    if (!t) throw new Error("<DraggableCore>: Unmounted during event!");
    return t
}
var nr = {},
    rr = {};
Object.defineProperty(rr, "__esModule", {
    value: !0
});
rr.default = cm;

function cm() {}

function Fn(e) {
    "@babel/helpers - typeof";
    return Fn = typeof Symbol == "function" && typeof Symbol.iterator == "symbol" ? function(t) {
        return typeof t
    } : function(t) {
        return t && typeof Symbol == "function" && t.constructor === Symbol && t !== Symbol.prototype ? "symbol" : typeof t
    }, Fn(e)
}
Object.defineProperty(nr, "__esModule", {
    value: !0
});
nr.default = void 0;
var Dr = um(u),
    he = Lo(ac),
    lm = Lo(on),
    oe = te,
    Ge = Ie,
    Rr = Le,
    Ut = Lo(rr);

function Lo(e) {
    return e && e.__esModule ? e : {
        default: e
    }
}

function vc(e) {
    if (typeof WeakMap != "function") return null;
    var t = new WeakMap,
        n = new WeakMap;
    return (vc = function(o) {
        return o ? n : t
    })(e)
}

function um(e, t) {
    if (!t && e && e.__esModule) return e;
    if (e === null || Fn(e) !== "object" && typeof e != "function") return {
        default: e
    };
    var n = vc(t);
    if (n && n.has(e)) return n.get(e);
    var r = {},
        o = Object.defineProperty && Object.getOwnPropertyDescriptor;
    for (var s in e)
        if (s !== "default" && Object.prototype.hasOwnProperty.call(e, s)) {
            var i = o ? Object.getOwnPropertyDescriptor(e, s) : null;
            i && (i.get || i.set) ? Object.defineProperty(r, s, i) : r[s] = e[s]
        } return r.default = e, n && n.set(e, r), r
}

function oi(e, t) {
    return pm(e) || hm(e, t) || dm(e, t) || fm()
}

function fm() {
    throw new TypeError(`Invalid attempt to destructure non-iterable instance.
In order to be iterable, non-array objects must have a [Symbol.iterator]() method.`)
}

function dm(e, t) {
    if (e) {
        if (typeof e == "string") return si(e, t);
        var n = Object.prototype.toString.call(e).slice(8, -1);
        if (n === "Object" && e.constructor && (n = e.constructor.name), n === "Map" || n === "Set") return Array.from(e);
        if (n === "Arguments" || /^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)) return si(e, t)
    }
}

function si(e, t) {
    (t == null || t > e.length) && (t = e.length);
    for (var n = 0, r = new Array(t); n < t; n++) r[n] = e[n];
    return r
}

function hm(e, t) {
    var n = e == null ? null : typeof Symbol < "u" && e[Symbol.iterator] || e["@@iterator"];
    if (n != null) {
        var r = [],
            o = !0,
            s = !1,
            i, a;
        try {
            for (n = n.call(e); !(o = (i = n.next()).done) && (r.push(i.value), !(t && r.length === t)); o = !0);
        } catch (c) {
            s = !0, a = c
        } finally {
            try {
                !o && n.return != null && n.return()
            } finally {
                if (s) throw a
            }
        }
        return r
    }
}

function pm(e) {
    if (Array.isArray(e)) return e
}

function mm(e, t) {
    if (!(e instanceof t)) throw new TypeError("Cannot call a class as a function")
}

function ii(e, t) {
    for (var n = 0; n < t.length; n++) {
        var r = t[n];
        r.enumerable = r.enumerable || !1, r.configurable = !0, "value" in r && (r.writable = !0), Object.defineProperty(e, r.key, r)
    }
}

function gm(e, t, n) {
    return t && ii(e.prototype, t), n && ii(e, n), Object.defineProperty(e, "prototype", {
        writable: !1
    }), e
}

function vm(e, t) {
    if (typeof t != "function" && t !== null) throw new TypeError("Super expression must either be null or a function");
    e.prototype = Object.create(t && t.prototype, {
        constructor: {
            value: e,
            writable: !0,
            configurable: !0
        }
    }), Object.defineProperty(e, "prototype", {
        writable: !1
    }), t && eo(e, t)
}

function eo(e, t) {
    return eo = Object.setPrototypeOf || function(r, o) {
        return r.__proto__ = o, r
    }, eo(e, t)
}

function ym(e) {
    var t = xm();
    return function() {
        var r = jn(e),
            o;
        if (t) {
            var s = jn(this).constructor;
            o = Reflect.construct(r, arguments, s)
        } else o = r.apply(this, arguments);
        return bm(this, o)
    }
}

function bm(e, t) {
    if (t && (Fn(t) === "object" || typeof t == "function")) return t;
    if (t !== void 0) throw new TypeError("Derived constructors may only return object or undefined");
    return se(e)
}

function se(e) {
    if (e === void 0) throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
    return e
}

function xm() {
    if (typeof Reflect > "u" || !Reflect.construct || Reflect.construct.sham) return !1;
    if (typeof Proxy == "function") return !0;
    try {
        return Boolean.prototype.valueOf.call(Reflect.construct(Boolean, [], function() {})), !0
    } catch {
        return !1
    }
}

function jn(e) {
    return jn = Object.setPrototypeOf ? Object.getPrototypeOf : function(n) {
        return n.__proto__ || Object.getPrototypeOf(n)
    }, jn(e)
}

function we(e, t, n) {
    return t in e ? Object.defineProperty(e, t, {
        value: n,
        enumerable: !0,
        configurable: !0,
        writable: !0
    }) : e[t] = n, e
}
var Ee = {
        touch: {
            start: "touchstart",
            move: "touchmove",
            stop: "touchend"
        },
        mouse: {
            start: "mousedown",
            move: "mousemove",
            stop: "mouseup"
        }
    },
    Ke = Ee.mouse,
    or = function(e) {
        vm(n, e);
        var t = ym(n);

        function n() {
            var r;
            mm(this, n);
            for (var o = arguments.length, s = new Array(o), i = 0; i < o; i++) s[i] = arguments[i];
            return r = t.call.apply(t, [this].concat(s)), we(se(r), "state", {
                dragging: !1,
                lastX: NaN,
                lastY: NaN,
                touchIdentifier: null
            }), we(se(r), "mounted", !1), we(se(r), "handleDragStart", function(a) {
                if (r.props.onMouseDown(a), !r.props.allowAnyClick && typeof a.button == "number" && a.button !== 0) return !1;
                var c = r.findDOMNode();
                if (!c || !c.ownerDocument || !c.ownerDocument.body) throw new Error("<DraggableCore> not mounted on DragStart!");
                var l = c.ownerDocument;
                if (!(r.props.disabled || !(a.target instanceof l.defaultView.Node) || r.props.handle && !(0, oe.matchesSelectorAndParentsTo)(a.target, r.props.handle, c) || r.props.cancel && (0, oe.matchesSelectorAndParentsTo)(a.target, r.props.cancel, c))) {
                    a.type === "touchstart" && a.preventDefault();
                    var f = (0, oe.getTouchIdentifier)(a);
                    r.setState({
                        touchIdentifier: f
                    });
                    var d = (0, Ge.getControlPosition)(a, f, se(r));
                    if (d != null) {
                        var h = d.x,
                            p = d.y,
                            g = (0, Ge.createCoreData)(se(r), h, p);
                        (0, Ut.default)("DraggableCore: handleDragStart: %j", g), (0, Ut.default)("calling", r.props.onStart);
                        var m = r.props.onStart(a, g);
                        m === !1 || r.mounted === !1 || (r.props.enableUserSelectHack && (0, oe.addUserSelectStyles)(l), r.setState({
                            dragging: !0,
                            lastX: h,
                            lastY: p
                        }), (0, oe.addEvent)(l, Ke.move, r.handleDrag), (0, oe.addEvent)(l, Ke.stop, r.handleDragStop))
                    }
                }
            }), we(se(r), "handleDrag", function(a) {
                var c = (0, Ge.getControlPosition)(a, r.state.touchIdentifier, se(r));
                if (c != null) {
                    var l = c.x,
                        f = c.y;
                    if (Array.isArray(r.props.grid)) {
                        var d = l - r.state.lastX,
                            h = f - r.state.lastY,
                            p = (0, Ge.snapToGrid)(r.props.grid, d, h),
                            g = oi(p, 2);
                        if (d = g[0], h = g[1], !d && !h) return;
                        l = r.state.lastX + d, f = r.state.lastY + h
                    }
                    var m = (0, Ge.createCoreData)(se(r), l, f);
                    (0, Ut.default)("DraggableCore: handleDrag: %j", m);
                    var b = r.props.onDrag(a, m);
                    if (b === !1 || r.mounted === !1) {
                        try {
                            r.handleDragStop(new MouseEvent("mouseup"))
                        } catch {
                            var x = document.createEvent("MouseEvents");
                            x.initMouseEvent("mouseup", !0, !0, window, 0, 0, 0, 0, 0, !1, !1, !1, !1, 0, null), r.handleDragStop(x)
                        }
                        return
                    }
                    r.setState({
                        lastX: l,
                        lastY: f
                    })
                }
            }), we(se(r), "handleDragStop", function(a) {
                if (r.state.dragging) {
                    var c = (0, Ge.getControlPosition)(a, r.state.touchIdentifier, se(r));
                    if (c != null) {
                        var l = c.x,
                            f = c.y;
                        if (Array.isArray(r.props.grid)) {
                            var d = l - r.state.lastX || 0,
                                h = f - r.state.lastY || 0,
                                p = (0, Ge.snapToGrid)(r.props.grid, d, h),
                                g = oi(p, 2);
                            d = g[0], h = g[1], l = r.state.lastX + d, f = r.state.lastY + h
                        }
                        var m = (0, Ge.createCoreData)(se(r), l, f),
                            b = r.props.onStop(a, m);
                        if (b === !1 || r.mounted === !1) return !1;
                        var x = r.findDOMNode();
                        x && r.props.enableUserSelectHack && (0, oe.removeUserSelectStyles)(x.ownerDocument), (0, Ut.default)("DraggableCore: handleDragStop: %j", m), r.setState({
                            dragging: !1,
                            lastX: NaN,
                            lastY: NaN
                        }), x && ((0, Ut.default)("DraggableCore: Removing handlers"), (0, oe.removeEvent)(x.ownerDocument, Ke.move, r.handleDrag), (0, oe.removeEvent)(x.ownerDocument, Ke.stop, r.handleDragStop))
                    }
                }
            }), we(se(r), "onMouseDown", function(a) {
                return Ke = Ee.mouse, r.handleDragStart(a)
            }), we(se(r), "onMouseUp", function(a) {
                return Ke = Ee.mouse, r.handleDragStop(a)
            }), we(se(r), "onTouchStart", function(a) {
                return Ke = Ee.touch, r.handleDragStart(a)
            }), we(se(r), "onTouchEnd", function(a) {
                return Ke = Ee.touch, r.handleDragStop(a)
            }), r
        }
        return gm(n, [{
            key: "componentDidMount",
            value: function() {
                this.mounted = !0;
                var o = this.findDOMNode();
                o && (0, oe.addEvent)(o, Ee.touch.start, this.onTouchStart, {
                    passive: !1
                })
            }
        }, {
            key: "componentWillUnmount",
            value: function() {
                this.mounted = !1;
                var o = this.findDOMNode();
                if (o) {
                    var s = o.ownerDocument;
                    (0, oe.removeEvent)(s, Ee.mouse.move, this.handleDrag), (0, oe.removeEvent)(s, Ee.touch.move, this.handleDrag), (0, oe.removeEvent)(s, Ee.mouse.stop, this.handleDragStop), (0, oe.removeEvent)(s, Ee.touch.stop, this.handleDragStop), (0, oe.removeEvent)(o, Ee.touch.start, this.onTouchStart, {
                        passive: !1
                    }), this.props.enableUserSelectHack && (0, oe.removeUserSelectStyles)(s)
                }
            }
        }, {
            key: "findDOMNode",
            value: function() {
                var o, s, i;
                return (o = this.props) !== null && o !== void 0 && o.nodeRef ? (s = this.props) === null || s === void 0 || (i = s.nodeRef) === null || i === void 0 ? void 0 : i.current : lm.default.findDOMNode(this)
            }
        }, {
            key: "render",
            value: function() {
                return Dr.cloneElement(Dr.Children.only(this.props.children), {
                    onMouseDown: this.onMouseDown,
                    onMouseUp: this.onMouseUp,
                    onTouchEnd: this.onTouchEnd
                })
            }
        }]), n
    }(Dr.Component);
nr.default = or;
we(or, "displayName", "DraggableCore");
we(or, "propTypes", {
    allowAnyClick: he.default.bool,
    disabled: he.default.bool,
    enableUserSelectHack: he.default.bool,
    offsetParent: function(t, n) {
        if (t[n] && t[n].nodeType !== 1) throw new Error("Draggable's offsetParent must be a DOM Node.")
    },
    grid: he.default.arrayOf(he.default.number),
    handle: he.default.string,
    cancel: he.default.string,
    nodeRef: he.default.object,
    onStart: he.default.func,
    onDrag: he.default.func,
    onStop: he.default.func,
    onMouseDown: he.default.func,
    scale: he.default.number,
    className: Rr.dontSetMe,
    style: Rr.dontSetMe,
    transform: Rr.dontSetMe
});
we(or, "defaultProps", {
    allowAnyClick: !1,
    disabled: !1,
    enableUserSelectHack: !0,
    onStart: function() {},
    onDrag: function() {},
    onStop: function() {},
    onMouseDown: function() {},
    scale: 1
});
(function(e) {
    function t(P) {
        "@babel/helpers - typeof";
        return t = typeof Symbol == "function" && typeof Symbol.iterator == "symbol" ? function(T) {
            return typeof T
        } : function(T) {
            return T && typeof Symbol == "function" && T.constructor === Symbol && T !== Symbol.prototype ? "symbol" : typeof T
        }, t(P)
    }
    Object.defineProperty(e, "__esModule", {
        value: !0
    }), Object.defineProperty(e, "DraggableCore", {
        enumerable: !0,
        get: function() {
            return l.default
        }
    }), e.default = void 0;
    var n = g(u),
        r = h(ac),
        o = h(on),
        s = h(Dp),
        i = te,
        a = Ie,
        c = Le,
        l = h(nr),
        f = h(rr),
        d = ["axis", "bounds", "children", "defaultPosition", "defaultClassName", "defaultClassNameDragging", "defaultClassNameDragged", "position", "positionOffset", "scale"];

    function h(P) {
        return P && P.__esModule ? P : {
            default: P
        }
    }

    function p(P) {
        if (typeof WeakMap != "function") return null;
        var T = new WeakMap,
            R = new WeakMap;
        return (p = function(O) {
            return O ? R : T
        })(P)
    }

    function g(P, T) {
        if (!T && P && P.__esModule) return P;
        if (P === null || t(P) !== "object" && typeof P != "function") return {
            default: P
        };
        var R = p(T);
        if (R && R.has(P)) return R.get(P);
        var D = {},
            O = Object.defineProperty && Object.getOwnPropertyDescriptor;
        for (var F in P)
            if (F !== "default" && Object.prototype.hasOwnProperty.call(P, F)) {
                var H = O ? Object.getOwnPropertyDescriptor(P, F) : null;
                H && (H.get || H.set) ? Object.defineProperty(D, F, H) : D[F] = P[F]
            } return D.default = P, R && R.set(P, D), D
    }

    function m() {
        return m = Object.assign || function(P) {
            for (var T = 1; T < arguments.length; T++) {
                var R = arguments[T];
                for (var D in R) Object.prototype.hasOwnProperty.call(R, D) && (P[D] = R[D])
            }
            return P
        }, m.apply(this, arguments)
    }

    function b(P, T) {
        if (P == null) return {};
        var R = x(P, T),
            D, O;
        if (Object.getOwnPropertySymbols) {
            var F = Object.getOwnPropertySymbols(P);
            for (O = 0; O < F.length; O++) D = F[O], !(T.indexOf(D) >= 0) && Object.prototype.propertyIsEnumerable.call(P, D) && (R[D] = P[D])
        }
        return R
    }

    function x(P, T) {
        if (P == null) return {};
        var R = {},
            D = Object.keys(P),
            O, F;
        for (F = 0; F < D.length; F++) O = D[F], !(T.indexOf(O) >= 0) && (R[O] = P[O]);
        return R
    }

    function v(P, T) {
        var R = Object.keys(P);
        if (Object.getOwnPropertySymbols) {
            var D = Object.getOwnPropertySymbols(P);
            T && (D = D.filter(function(O) {
                return Object.getOwnPropertyDescriptor(P, O).enumerable
            })), R.push.apply(R, D)
        }
        return R
    }

    function y(P) {
        for (var T = 1; T < arguments.length; T++) {
            var R = arguments[T] != null ? arguments[T] : {};
            T % 2 ? v(Object(R), !0).forEach(function(D) {
                Y(P, D, R[D])
            }) : Object.getOwnPropertyDescriptors ? Object.defineProperties(P, Object.getOwnPropertyDescriptors(R)) : v(Object(R)).forEach(function(D) {
                Object.defineProperty(P, D, Object.getOwnPropertyDescriptor(R, D))
            })
        }
        return P
    }

    function C(P, T) {
        return E(P) || S(P, T) || A(P, T) || $()
    }

    function $() {
        throw new TypeError(`Invalid attempt to destructure non-iterable instance.
In order to be iterable, non-array objects must have a [Symbol.iterator]() method.`)
    }

    function A(P, T) {
        if (P) {
            if (typeof P == "string") return w(P, T);
            var R = Object.prototype.toString.call(P).slice(8, -1);
            if (R === "Object" && P.constructor && (R = P.constructor.name), R === "Map" || R === "Set") return Array.from(P);
            if (R === "Arguments" || /^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(R)) return w(P, T)
        }
    }

    function w(P, T) {
        (T == null || T > P.length) && (T = P.length);
        for (var R = 0, D = new Array(T); R < T; R++) D[R] = P[R];
        return D
    }

    function S(P, T) {
        var R = P == null ? null : typeof Symbol < "u" && P[Symbol.iterator] || P["@@iterator"];
        if (R != null) {
            var D = [],
                O = !0,
                F = !1,
                H, z;
            try {
                for (R = R.call(P); !(O = (H = R.next()).done) && (D.push(H.value), !(T && D.length === T)); O = !0);
            } catch (X) {
                F = !0, z = X
            } finally {
                try {
                    !O && R.return != null && R.return()
                } finally {
                    if (F) throw z
                }
            }
            return D
        }
    }

    function E(P) {
        if (Array.isArray(P)) return P
    }

    function j(P, T) {
        if (!(P instanceof T)) throw new TypeError("Cannot call a class as a function")
    }

    function I(P, T) {
        for (var R = 0; R < T.length; R++) {
            var D = T[R];
            D.enumerable = D.enumerable || !1, D.configurable = !0, "value" in D && (D.writable = !0), Object.defineProperty(P, D.key, D)
        }
    }

    function G(P, T, R) {
        return T && I(P.prototype, T), R && I(P, R), Object.defineProperty(P, "prototype", {
            writable: !1
        }), P
    }

    function _(P, T) {
        if (typeof T != "function" && T !== null) throw new TypeError("Super expression must either be null or a function");
        P.prototype = Object.create(T && T.prototype, {
            constructor: {
                value: P,
                writable: !0,
                configurable: !0
            }
        }), Object.defineProperty(P, "prototype", {
            writable: !1
        }), T && L(P, T)
    }

    function L(P, T) {
        return L = Object.setPrototypeOf || function(D, O) {
            return D.__proto__ = O, D
        }, L(P, T)
    }

    function k(P) {
        var T = q();
        return function() {
            var D = N(P),
                O;
            if (T) {
                var F = N(this).constructor;
                O = Reflect.construct(D, arguments, F)
            } else O = D.apply(this, arguments);
            return U(this, O)
        }
    }

    function U(P, T) {
        if (T && (t(T) === "object" || typeof T == "function")) return T;
        if (T !== void 0) throw new TypeError("Derived constructors may only return object or undefined");
        return Z(P)
    }

    function Z(P) {
        if (P === void 0) throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
        return P
    }

    function q() {
        if (typeof Reflect > "u" || !Reflect.construct || Reflect.construct.sham) return !1;
        if (typeof Proxy == "function") return !0;
        try {
            return Boolean.prototype.valueOf.call(Reflect.construct(Boolean, [], function() {})), !0
        } catch {
            return !1
        }
    }

    function N(P) {
        return N = Object.setPrototypeOf ? Object.getPrototypeOf : function(R) {
            return R.__proto__ || Object.getPrototypeOf(R)
        }, N(P)
    }

    function Y(P, T, R) {
        return T in P ? Object.defineProperty(P, T, {
            value: R,
            enumerable: !0,
            configurable: !0,
            writable: !0
        }) : P[T] = R, P
    }
    var W = function(P) {
        _(R, P);
        var T = k(R);

        function R(D) {
            var O;
            return j(this, R), O = T.call(this, D), Y(Z(O), "onDragStart", function(F, H) {
                (0, f.default)("Draggable: onDragStart: %j", H);
                var z = O.props.onStart(F, (0, a.createDraggableData)(Z(O), H));
                if (z === !1) return !1;
                O.setState({
                    dragging: !0,
                    dragged: !0
                })
            }), Y(Z(O), "onDrag", function(F, H) {
                if (!O.state.dragging) return !1;
                (0, f.default)("Draggable: onDrag: %j", H);
                var z = (0, a.createDraggableData)(Z(O), H),
                    X = {
                        x: z.x,
                        y: z.y
                    };
                if (O.props.bounds) {
                    var ae = X.x,
                        $e = X.y;
                    X.x += O.state.slackX, X.y += O.state.slackY;
                    var Pe = (0, a.getBoundPosition)(Z(O), X.x, X.y),
                        Se = C(Pe, 2),
                        Ft = Se[0],
                        gt = Se[1];
                    X.x = Ft, X.y = gt, X.slackX = O.state.slackX + (ae - X.x), X.slackY = O.state.slackY + ($e - X.y), z.x = X.x, z.y = X.y, z.deltaX = X.x - O.state.x, z.deltaY = X.y - O.state.y
                }
                var vt = O.props.onDrag(F, z);
                if (vt === !1) return !1;
                O.setState(X)
            }), Y(Z(O), "onDragStop", function(F, H) {
                if (!O.state.dragging) return !1;
                var z = O.props.onStop(F, (0, a.createDraggableData)(Z(O), H));
                if (z === !1) return !1;
                (0, f.default)("Draggable: onDragStop: %j", H);
                var X = {
                        dragging: !1,
                        slackX: 0,
                        slackY: 0
                    },
                    ae = !!O.props.position;
                if (ae) {
                    var $e = O.props.position,
                        Pe = $e.x,
                        Se = $e.y;
                    X.x = Pe, X.y = Se
                }
                O.setState(X)
            }), O.state = {
                dragging: !1,
                dragged: !1,
                x: D.position ? D.position.x : D.defaultPosition.x,
                y: D.position ? D.position.y : D.defaultPosition.y,
                prevPropsPosition: y({}, D.position),
                slackX: 0,
                slackY: 0,
                isElementSVG: !1
            }, D.position && !(D.onDrag || D.onStop) && console.warn("A `position` was applied to this <Draggable>, without drag handlers. This will make this component effectively undraggable. Please attach `onDrag` or `onStop` handlers so you can adjust the `position` of this element."), O
        }
        return G(R, [{
            key: "componentDidMount",
            value: function() {
                typeof window.SVGElement < "u" && this.findDOMNode() instanceof window.SVGElement && this.setState({
                    isElementSVG: !0
                })
            }
        }, {
            key: "componentWillUnmount",
            value: function() {
                this.setState({
                    dragging: !1
                })
            }
        }, {
            key: "findDOMNode",
            value: function() {
                var O, F, H;
                return (O = (F = this.props) === null || F === void 0 || (H = F.nodeRef) === null || H === void 0 ? void 0 : H.current) !== null && O !== void 0 ? O : o.default.findDOMNode(this)
            }
        }, {
            key: "render",
            value: function() {
                var O, F = this.props;
                F.axis, F.bounds;
                var H = F.children,
                    z = F.defaultPosition,
                    X = F.defaultClassName,
                    ae = F.defaultClassNameDragging,
                    $e = F.defaultClassNameDragged,
                    Pe = F.position,
                    Se = F.positionOffset;
                F.scale;
                var Ft = b(F, d),
                    gt = {},
                    vt = null,
                    mr = !!Pe,
                    dn = !mr || this.state.dragging,
                    it = Pe || z,
                    qo = {
                        x: (0, a.canDragX)(this) && dn ? this.state.x : it.x,
                        y: (0, a.canDragY)(this) && dn ? this.state.y : it.y
                    };
                this.state.isElementSVG ? vt = (0, i.createSVGTransform)(qo, Se) : gt = (0, i.createCSSTransform)(qo, Se);
                var Ol = (0, s.default)(H.props.className || "", X, (O = {}, Y(O, ae, this.state.dragging), Y(O, $e, this.state.dragged), O));
                return n.createElement(l.default, m({}, Ft, {
                    onStart: this.onDragStart,
                    onDrag: this.onDrag,
                    onStop: this.onDragStop
                }), n.cloneElement(n.Children.only(H), {
                    className: Ol,
                    style: y(y({}, H.props.style), gt),
                    transform: vt
                }))
            }
        }], [{
            key: "getDerivedStateFromProps",
            value: function(O, F) {
                var H = O.position,
                    z = F.prevPropsPosition;
                return H && (!z || H.x !== z.x || H.y !== z.y) ? ((0, f.default)("Draggable: getDerivedStateFromProps %j", {
                    position: H,
                    prevPropsPosition: z
                }), {
                    x: H.x,
                    y: H.y,
                    prevPropsPosition: y({}, H)
                }) : null
            }
        }]), R
    }(n.Component);
    e.default = W, Y(W, "displayName", "Draggable"), Y(W, "propTypes", y(y({}, l.default.propTypes), {}, {
        axis: r.default.oneOf(["both", "x", "y", "none"]),
        bounds: r.default.oneOfType([r.default.shape({
            left: r.default.number,
            right: r.default.number,
            top: r.default.number,
            bottom: r.default.number
        }), r.default.string, r.default.oneOf([!1])]),
        defaultClassName: r.default.string,
        defaultClassNameDragging: r.default.string,
        defaultClassNameDragged: r.default.string,
        defaultPosition: r.default.shape({
            x: r.default.number,
            y: r.default.number
        }),
        positionOffset: r.default.shape({
            x: r.default.oneOfType([r.default.number, r.default.string]),
            y: r.default.oneOfType([r.default.number, r.default.string])
        }),
        position: r.default.shape({
            x: r.default.number,
            y: r.default.number
        }),
        className: c.dontSetMe,
        style: c.dontSetMe,
        transform: c.dontSetMe
    })), Y(W, "defaultProps", y(y({}, l.default.defaultProps), {}, {
        axis: "both",
        bounds: !1,
        defaultClassName: "react-draggable",
        defaultClassNameDragging: "react-draggable-dragging",
        defaultClassNameDragged: "react-draggable-dragged",
        defaultPosition: {
            x: 0,
            y: 0
        },
        scale: 1
    }))
})(rc);
var yc = rc,
    bc = yc.default,
    wm = yc.DraggableCore;
tr.exports = bc;
tr.exports.default = bc;
tr.exports.DraggableCore = wm;
var Cm = tr.exports;
const Am = Li(Cm);

function V() {
    return V = Object.assign ? Object.assign.bind() : function(e) {
        for (var t = 1; t < arguments.length; t++) {
            var n = arguments[t];
            for (var r in n) Object.prototype.hasOwnProperty.call(n, r) && (e[r] = n[r])
        }
        return e
    }, V.apply(this, arguments)
}

function $m(e, t) {
    typeof e == "function" ? e(t) : e != null && (e.current = t)
}

function sr(...e) {
    return t => e.forEach(n => $m(n, t))
}

function le(...e) {
    return u.useCallback(sr(...e), e)
}
const Rt = u.forwardRef((e, t) => {
    const {
        children: n,
        ...r
    } = e, o = u.Children.toArray(n), s = o.find(Pm);
    if (s) {
        const i = s.props.children,
            a = o.map(c => c === s ? u.Children.count(i) > 1 ? u.Children.only(null) : u.isValidElement(i) ? i.props.children : null : c);
        return u.createElement(to, V({}, r, {
            ref: t
        }), u.isValidElement(i) ? u.cloneElement(i, void 0, a) : null)
    }
    return u.createElement(to, V({}, r, {
        ref: t
    }), n)
});
Rt.displayName = "Slot";
const to = u.forwardRef((e, t) => {
    const {
        children: n,
        ...r
    } = e;
    return u.isValidElement(n) ? u.cloneElement(n, {
        ...Sm(r, n.props),
        ref: t ? sr(t, n.ref) : n.ref
    }) : u.Children.count(n) > 1 ? u.Children.only(null) : null
});
to.displayName = "SlotClone";
const xc = ({
    children: e
}) => u.createElement(u.Fragment, null, e);

function Pm(e) {
    return u.isValidElement(e) && e.type === xc
}

function Sm(e, t) {
    const n = {
        ...t
    };
    for (const r in t) {
        const o = e[r],
            s = t[r];
        /^on[A-Z]/.test(r) ? o && s ? n[r] = (...a) => {
            s(...a), o(...a)
        } : o && (n[r] = o) : r === "style" ? n[r] = {
            ...o,
            ...s
        } : r === "className" && (n[r] = [o, s].filter(Boolean).join(" "))
    }
    return {
        ...e,
        ...n
    }
}
const ai = e => typeof e == "boolean" ? "".concat(e) : e === 0 ? "0" : e,
    ci = Tt,
    Em = (e, t) => n => {
        var r;
        if (t?.variants == null) return ci(e, n?.class, n?.className);
        const {
            variants: o,
            defaultVariants: s
        } = t, i = Object.keys(o).map(l => {
            const f = n?.[l],
                d = s?.[l];
            if (f === null) return null;
            const h = ai(f) || ai(d);
            return o[l][h]
        }), a = n && Object.entries(n).reduce((l, f) => {
            let [d, h] = f;
            return h === void 0 || (l[d] = h), l
        }, {}), c = t == null || (r = t.compoundVariants) === null || r === void 0 ? void 0 : r.reduce((l, f) => {
            let {
                class: d,
                className: h,
                ...p
            } = f;
            return Object.entries(p).every(g => {
                let [m, b] = g;
                return Array.isArray(b) ? b.includes({
                    ...s,
                    ...a
                } [m]) : {
                    ...s,
                    ...a
                } [m] === b
            }) ? [...l, d, h] : l
        }, []);
        return ci(e, i, c, n?.class, n?.className)
    },
    Tm = Em("inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50", {
        variants: {
            variant: {
                default: "bg-primary text-primary-foreground hover:bg-primary/90",
                destructive: "bg-destructive text-destructive-foreground hover:bg-destructive/90",
                outline: "border border-input bg-background hover:bg-accent hover:text-accent-foreground",
                secondary: "bg-secondary text-secondary-foreground hover:bg-secondary/80",
                ghost: "hover:bg-accent hover:text-accent-foreground text-foreground",
                link: "text-primary underline-offset-4 hover:underline"
            },
            size: {
                default: "h-10 px-4 py-2",
                sm: "h-9 rounded-md px-3",
                lg: "h-11 rounded-md px-8",
                icon: "h-10 w-10"
            }
        },
        defaultVariants: {
            variant: "default",
            size: "default"
        }
    }),
    Ht = u.forwardRef(({
        className: e,
        variant: t,
        size: n,
        asChild: r = !1,
        ...o
    }, s) => {
        const i = r ? Rt : "button";
        return M.jsx(i, {
            className: ke(Tm({
                variant: t,
                size: n,
                className: e
            })),
            ref: s,
            ...o
        })
    });
Ht.displayName = "Button";

function K(e, t, {
    checkForDefaultPrevented: n = !0
} = {}) {
    return function(o) {
        if (e?.(o), n === !1 || !o.defaultPrevented) return t?.(o)
    }
}

function Lt(e, t = []) {
    let n = [];

    function r(s, i) {
        const a = u.createContext(i),
            c = n.length;
        n = [...n, i];

        function l(d) {
            const {
                scope: h,
                children: p,
                ...g
            } = d, m = h?.[e][c] || a, b = u.useMemo(() => g, Object.values(g));
            return u.createElement(m.Provider, {
                value: b
            }, p)
        }

        function f(d, h) {
            const p = h?.[e][c] || a,
                g = u.useContext(p);
            if (g) return g;
            if (i !== void 0) return i;
            throw new Error(`\`${d}\` must be used within \`${s}\``)
        }
        return l.displayName = s + "Provider", [l, f]
    }
    const o = () => {
        const s = n.map(i => u.createContext(i));
        return function(a) {
            const c = a?.[e] || s;
            return u.useMemo(() => ({
                [`__scope${e}`]: {
                    ...a,
                    [e]: c
                }
            }), [a, c])
        }
    };
    return o.scopeName = e, [r, Mm(o, ...t)]
}

function Mm(...e) {
    const t = e[0];
    if (e.length === 1) return t;
    const n = () => {
        const r = e.map(o => ({
            useScope: o(),
            scopeName: o.scopeName
        }));
        return function(s) {
            const i = r.reduce((a, {
                useScope: c,
                scopeName: l
            }) => {
                const d = c(s)[`__scope${l}`];
                return {
                    ...a,
                    ...d
                }
            }, {});
            return u.useMemo(() => ({
                [`__scope${t.scopeName}`]: i
            }), [i])
        }
    };
    return n.scopeName = t.scopeName, n
}

function Ce(e) {
    const t = u.useRef(e);
    return u.useEffect(() => {
        t.current = e
    }), u.useMemo(() => (...n) => {
        var r;
        return (r = t.current) === null || r === void 0 ? void 0 : r.call(t, ...n)
    }, [])
}

function ir({
    prop: e,
    defaultProp: t,
    onChange: n = () => {}
}) {
    const [r, o] = Dm({
        defaultProp: t,
        onChange: n
    }), s = e !== void 0, i = s ? e : r, a = Ce(n), c = u.useCallback(l => {
        if (s) {
            const d = typeof l == "function" ? l(e) : l;
            d !== e && a(d)
        } else o(l)
    }, [s, e, o, a]);
    return [i, c]
}

function Dm({
    defaultProp: e,
    onChange: t
}) {
    const n = u.useState(e),
        [r] = n,
        o = u.useRef(r),
        s = Ce(t);
    return u.useEffect(() => {
        o.current !== r && (s(r), o.current = r)
    }, [r, o, s]), n
}
const Rm = ["a", "button", "div", "form", "h2", "h3", "img", "input", "label", "li", "nav", "ol", "p", "span", "svg", "ul"],
    ue = Rm.reduce((e, t) => {
        const n = u.forwardRef((r, o) => {
            const {
                asChild: s,
                ...i
            } = r, a = s ? Rt : t;
            return u.useEffect(() => {
                window[Symbol.for("radix-ui")] = !0
            }, []), u.createElement(a, V({}, i, {
                ref: o
            }))
        });
        return n.displayName = `Primitive.${t}`, {
            ...e,
            [t]: n
        }
    }, {});

function wc(e, t) {
    e && on.flushSync(() => e.dispatchEvent(t))
}

function Cc(e) {
    const t = e + "CollectionProvider",
        [n, r] = Lt(t),
        [o, s] = n(t, {
            collectionRef: {
                current: null
            },
            itemMap: new Map
        }),
        i = p => {
            const {
                scope: g,
                children: m
            } = p, b = me.useRef(null), x = me.useRef(new Map).current;
            return me.createElement(o, {
                scope: g,
                itemMap: x,
                collectionRef: b
            }, m)
        },
        a = e + "CollectionSlot",
        c = me.forwardRef((p, g) => {
            const {
                scope: m,
                children: b
            } = p, x = s(a, m), v = le(g, x.collectionRef);
            return me.createElement(Rt, {
                ref: v
            }, b)
        }),
        l = e + "CollectionItemSlot",
        f = "data-radix-collection-item",
        d = me.forwardRef((p, g) => {
            const {
                scope: m,
                children: b,
                ...x
            } = p, v = me.useRef(null), y = le(g, v), C = s(l, m);
            return me.useEffect(() => (C.itemMap.set(v, {
                ref: v,
                ...x
            }), () => void C.itemMap.delete(v))), me.createElement(Rt, {
                [f]: "",
                ref: y
            }, b)
        });

    function h(p) {
        const g = s(e + "CollectionConsumer", p);
        return me.useCallback(() => {
            const b = g.collectionRef.current;
            if (!b) return [];
            const x = Array.from(b.querySelectorAll(`[${f}]`));
            return Array.from(g.itemMap.values()).sort((C, $) => x.indexOf(C.ref.current) - x.indexOf($.ref.current))
        }, [g.collectionRef, g.itemMap])
    }
    return [{
        Provider: i,
        Slot: c,
        ItemSlot: d
    }, h, r]
}
const Om = u.createContext(void 0);

function Ac(e) {
    const t = u.useContext(Om);
    return e || t || "ltr"
}

function _m(e, t = globalThis?.document) {
    const n = Ce(e);
    u.useEffect(() => {
        const r = o => {
            o.key === "Escape" && n(o)
        };
        return t.addEventListener("keydown", r), () => t.removeEventListener("keydown", r)
    }, [n, t])
}
const no = "dismissableLayer.update",
    km = "dismissableLayer.pointerDownOutside",
    Lm = "dismissableLayer.focusOutside";
let li;
const Im = u.createContext({
        layers: new Set,
        layersWithOutsidePointerEventsDisabled: new Set,
        branches: new Set
    }),
    $c = u.forwardRef((e, t) => {
        var n;
        const {
            disableOutsidePointerEvents: r = !1,
            onEscapeKeyDown: o,
            onPointerDownOutside: s,
            onFocusOutside: i,
            onInteractOutside: a,
            onDismiss: c,
            ...l
        } = e, f = u.useContext(Im), [d, h] = u.useState(null), p = (n = d?.ownerDocument) !== null && n !== void 0 ? n : globalThis?.document, [, g] = u.useState({}), m = le(t, S => h(S)), b = Array.from(f.layers), [x] = [...f.layersWithOutsidePointerEventsDisabled].slice(-1), v = b.indexOf(x), y = d ? b.indexOf(d) : -1, C = f.layersWithOutsidePointerEventsDisabled.size > 0, $ = y >= v, A = Vm(S => {
            const E = S.target,
                j = [...f.branches].some(I => I.contains(E));
            !$ || j || (s?.(S), a?.(S), S.defaultPrevented || c?.())
        }, p), w = Nm(S => {
            const E = S.target;
            [...f.branches].some(I => I.contains(E)) || (i?.(S), a?.(S), S.defaultPrevented || c?.())
        }, p);
        return _m(S => {
            y === f.layers.size - 1 && (o?.(S), !S.defaultPrevented && c && (S.preventDefault(), c()))
        }, p), u.useEffect(() => {
            if (d) return r && (f.layersWithOutsidePointerEventsDisabled.size === 0 && (li = p.body.style.pointerEvents, p.body.style.pointerEvents = "none"), f.layersWithOutsidePointerEventsDisabled.add(d)), f.layers.add(d), ui(), () => {
                r && f.layersWithOutsidePointerEventsDisabled.size === 1 && (p.body.style.pointerEvents = li)
            }
        }, [d, p, r, f]), u.useEffect(() => () => {
            d && (f.layers.delete(d), f.layersWithOutsidePointerEventsDisabled.delete(d), ui())
        }, [d, f]), u.useEffect(() => {
            const S = () => g({});
            return document.addEventListener(no, S), () => document.removeEventListener(no, S)
        }, []), u.createElement(ue.div, V({}, l, {
            ref: m,
            style: {
                pointerEvents: C ? $ ? "auto" : "none" : void 0,
                ...e.style
            },
            onFocusCapture: K(e.onFocusCapture, w.onFocusCapture),
            onBlurCapture: K(e.onBlurCapture, w.onBlurCapture),
            onPointerDownCapture: K(e.onPointerDownCapture, A.onPointerDownCapture)
        }))
    });

function Vm(e, t = globalThis?.document) {
    const n = Ce(e),
        r = u.useRef(!1),
        o = u.useRef(() => {});
    return u.useEffect(() => {
        const s = a => {
                if (a.target && !r.current) {
                    let l = function() {
                        Pc(km, n, c, {
                            discrete: !0
                        })
                    };
                    const c = {
                        originalEvent: a
                    };
                    a.pointerType === "touch" ? (t.removeEventListener("click", o.current), o.current = l, t.addEventListener("click", o.current, {
                        once: !0
                    })) : l()
                }
                r.current = !1
            },
            i = window.setTimeout(() => {
                t.addEventListener("pointerdown", s)
            }, 0);
        return () => {
            window.clearTimeout(i), t.removeEventListener("pointerdown", s), t.removeEventListener("click", o.current)
        }
    }, [t, n]), {
        onPointerDownCapture: () => r.current = !0
    }
}

function Nm(e, t = globalThis?.document) {
    const n = Ce(e),
        r = u.useRef(!1);
    return u.useEffect(() => {
        const o = s => {
            s.target && !r.current && Pc(Lm, n, {
                originalEvent: s
            }, {
                discrete: !1
            })
        };
        return t.addEventListener("focusin", o), () => t.removeEventListener("focusin", o)
    }, [t, n]), {
        onFocusCapture: () => r.current = !0,
        onBlurCapture: () => r.current = !1
    }
}

function ui() {
    const e = new CustomEvent(no);
    document.dispatchEvent(e)
}

function Pc(e, t, n, {
    discrete: r
}) {
    const o = n.originalEvent.target,
        s = new CustomEvent(e, {
            bubbles: !1,
            cancelable: !0,
            detail: n
        });
    t && o.addEventListener(e, t, {
        once: !0
    }), r ? wc(o, s) : o.dispatchEvent(s)
}
let Or = 0;

function Fm() {
    u.useEffect(() => {
        var e, t;
        const n = document.querySelectorAll("[data-radix-focus-guard]");
        return document.body.insertAdjacentElement("afterbegin", (e = n[0]) !== null && e !== void 0 ? e : fi()), document.body.insertAdjacentElement("beforeend", (t = n[1]) !== null && t !== void 0 ? t : fi()), Or++, () => {
            Or === 1 && document.querySelectorAll("[data-radix-focus-guard]").forEach(r => r.remove()), Or--
        }
    }, [])
}

function fi() {
    const e = document.createElement("span");
    return e.setAttribute("data-radix-focus-guard", ""), e.tabIndex = 0, e.style.cssText = "outline: none; opacity: 0; position: fixed; pointer-events: none", e
}
const _r = "focusScope.autoFocusOnMount",
    kr = "focusScope.autoFocusOnUnmount",
    di = {
        bubbles: !1,
        cancelable: !0
    },
    jm = u.forwardRef((e, t) => {
        const {
            loop: n = !1,
            trapped: r = !1,
            onMountAutoFocus: o,
            onUnmountAutoFocus: s,
            ...i
        } = e, [a, c] = u.useState(null), l = Ce(o), f = Ce(s), d = u.useRef(null), h = le(t, m => c(m)), p = u.useRef({
            paused: !1,
            pause() {
                this.paused = !0
            },
            resume() {
                this.paused = !1
            }
        }).current;
        u.useEffect(() => {
            if (r) {
                let m = function(y) {
                        if (p.paused || !a) return;
                        const C = y.target;
                        a.contains(C) ? d.current = C : Ye(d.current, {
                            select: !0
                        })
                    },
                    b = function(y) {
                        if (p.paused || !a) return;
                        const C = y.relatedTarget;
                        C !== null && (a.contains(C) || Ye(d.current, {
                            select: !0
                        }))
                    },
                    x = function(y) {
                        const C = document.activeElement;
                        for (const $ of y) $.removedNodes.length > 0 && (a != null && a.contains(C) || Ye(a))
                    };
                document.addEventListener("focusin", m), document.addEventListener("focusout", b);
                const v = new MutationObserver(x);
                return a && v.observe(a, {
                    childList: !0,
                    subtree: !0
                }), () => {
                    document.removeEventListener("focusin", m), document.removeEventListener("focusout", b), v.disconnect()
                }
            }
        }, [r, a, p.paused]), u.useEffect(() => {
            if (a) {
                pi.add(p);
                const m = document.activeElement;
                if (!a.contains(m)) {
                    const x = new CustomEvent(_r, di);
                    a.addEventListener(_r, l), a.dispatchEvent(x), x.defaultPrevented || (Bm(Km(Sc(a)), {
                        select: !0
                    }), document.activeElement === m && Ye(a))
                }
                return () => {
                    a.removeEventListener(_r, l), setTimeout(() => {
                        const x = new CustomEvent(kr, di);
                        a.addEventListener(kr, f), a.dispatchEvent(x), x.defaultPrevented || Ye(m ?? document.body, {
                            select: !0
                        }), a.removeEventListener(kr, f), pi.remove(p)
                    }, 0)
                }
            }
        }, [a, l, f, p]);
        const g = u.useCallback(m => {
            if (!n && !r || p.paused) return;
            const b = m.key === "Tab" && !m.altKey && !m.ctrlKey && !m.metaKey,
                x = document.activeElement;
            if (b && x) {
                const v = m.currentTarget,
                    [y, C] = Um(v);
                y && C ? !m.shiftKey && x === C ? (m.preventDefault(), n && Ye(y, {
                    select: !0
                })) : m.shiftKey && x === y && (m.preventDefault(), n && Ye(C, {
                    select: !0
                })) : x === v && m.preventDefault()
            }
        }, [n, r, p.paused]);
        return u.createElement(ue.div, V({
            tabIndex: -1
        }, i, {
            ref: h,
            onKeyDown: g
        }))
    });

function Bm(e, {
    select: t = !1
} = {}) {
    const n = document.activeElement;
    for (const r of e)
        if (Ye(r, {
                select: t
            }), document.activeElement !== n) return
}

function Um(e) {
    const t = Sc(e),
        n = hi(t, e),
        r = hi(t.reverse(), e);
    return [n, r]
}

function Sc(e) {
    const t = [],
        n = document.createTreeWalker(e, NodeFilter.SHOW_ELEMENT, {
            acceptNode: r => {
                const o = r.tagName === "INPUT" && r.type === "hidden";
                return r.disabled || r.hidden || o ? NodeFilter.FILTER_SKIP : r.tabIndex >= 0 ? NodeFilter.FILTER_ACCEPT : NodeFilter.FILTER_SKIP
            }
        });
    for (; n.nextNode();) t.push(n.currentNode);
    return t
}

function hi(e, t) {
    for (const n of e)
        if (!Wm(n, {
                upTo: t
            })) return n
}

function Wm(e, {
    upTo: t
}) {
    if (getComputedStyle(e).visibility === "hidden") return !0;
    for (; e;) {
        if (t !== void 0 && e === t) return !1;
        if (getComputedStyle(e).display === "none") return !0;
        e = e.parentElement
    }
    return !1
}

function Hm(e) {
    return e instanceof HTMLInputElement && "select" in e
}

function Ye(e, {
    select: t = !1
} = {}) {
    if (e && e.focus) {
        const n = document.activeElement;
        e.focus({
            preventScroll: !0
        }), e !== n && Hm(e) && t && e.select()
    }
}
const pi = Gm();

function Gm() {
    let e = [];
    return {
        add(t) {
            const n = e[0];
            t !== n && n?.pause(), e = mi(e, t), e.unshift(t)
        },
        remove(t) {
            var n;
            e = mi(e, t), (n = e[0]) === null || n === void 0 || n.resume()
        }
    }
}

function mi(e, t) {
    const n = [...e],
        r = n.indexOf(t);
    return r !== -1 && n.splice(r, 1), n
}

function Km(e) {
    return e.filter(t => t.tagName !== "A")
}
const Ot = globalThis?.document ? u.useLayoutEffect : () => {},
    zm = jl["useId".toString()] || (() => {});
let Ym = 0;

function _t(e) {
    const [t, n] = u.useState(zm());
    return Ot(() => {
        e || n(r => r ?? String(Ym++))
    }, [e]), e || (t ? `radix-${t}` : "")
}
const Xm = ["top", "right", "bottom", "left"],
    et = Math.min,
    ge = Math.max,
    Bn = Math.round,
    yn = Math.floor,
    tt = e => ({
        x: e,
        y: e
    }),
    Zm = {
        left: "right",
        right: "left",
        bottom: "top",
        top: "bottom"
    },
    qm = {
        start: "end",
        end: "start"
    };

function ro(e, t, n) {
    return ge(e, et(t, n))
}

function Be(e, t) {
    return typeof e == "function" ? e(t) : e
}

function Ue(e) {
    return e.split("-")[0]
}

function It(e) {
    return e.split("-")[1]
}

function Io(e) {
    return e === "x" ? "y" : "x"
}

function Vo(e) {
    return e === "y" ? "height" : "width"
}

function Vt(e) {
    return ["top", "bottom"].includes(Ue(e)) ? "y" : "x"
}

function No(e) {
    return Io(Vt(e))
}

function Qm(e, t, n) {
    n === void 0 && (n = !1);
    const r = It(e),
        o = No(e),
        s = Vo(o);
    let i = o === "x" ? r === (n ? "end" : "start") ? "right" : "left" : r === "start" ? "bottom" : "top";
    return t.reference[s] > t.floating[s] && (i = Un(i)), [i, Un(i)]
}

function Jm(e) {
    const t = Un(e);
    return [oo(e), t, oo(t)]
}

function oo(e) {
    return e.replace(/start|end/g, t => qm[t])
}

function eg(e, t, n) {
    const r = ["left", "right"],
        o = ["right", "left"],
        s = ["top", "bottom"],
        i = ["bottom", "top"];
    switch (e) {
        case "top":
        case "bottom":
            return n ? t ? o : r : t ? r : o;
        case "left":
        case "right":
            return t ? s : i;
        default:
            return []
    }
}

function tg(e, t, n, r) {
    const o = It(e);
    let s = eg(Ue(e), n === "start", r);
    return o && (s = s.map(i => i + "-" + o), t && (s = s.concat(s.map(oo)))), s
}

function Un(e) {
    return e.replace(/left|right|bottom|top/g, t => Zm[t])
}

function ng(e) {
    return {
        top: 0,
        right: 0,
        bottom: 0,
        left: 0,
        ...e
    }
}

function Ec(e) {
    return typeof e != "number" ? ng(e) : {
        top: e,
        right: e,
        bottom: e,
        left: e
    }
}

function Wn(e) {
    return {
        ...e,
        top: e.y,
        left: e.x,
        right: e.x + e.width,
        bottom: e.y + e.height
    }
}

function gi(e, t, n) {
    let {
        reference: r,
        floating: o
    } = e;
    const s = Vt(t),
        i = No(t),
        a = Vo(i),
        c = Ue(t),
        l = s === "y",
        f = r.x + r.width / 2 - o.width / 2,
        d = r.y + r.height / 2 - o.height / 2,
        h = r[a] / 2 - o[a] / 2;
    let p;
    switch (c) {
        case "top":
            p = {
                x: f,
                y: r.y - o.height
            };
            break;
        case "bottom":
            p = {
                x: f,
                y: r.y + r.height
            };
            break;
        case "right":
            p = {
                x: r.x + r.width,
                y: d
            };
            break;
        case "left":
            p = {
                x: r.x - o.width,
                y: d
            };
            break;
        default:
            p = {
                x: r.x,
                y: r.y
            }
    }
    switch (It(t)) {
        case "start":
            p[i] -= h * (n && l ? -1 : 1);
            break;
        case "end":
            p[i] += h * (n && l ? -1 : 1);
            break
    }
    return p
}
const rg = async (e, t, n) => {
    const {
        placement: r = "bottom",
        strategy: o = "absolute",
        middleware: s = [],
        platform: i
    } = n, a = s.filter(Boolean), c = await (i.isRTL == null ? void 0 : i.isRTL(t));
    let l = await i.getElementRects({
            reference: e,
            floating: t,
            strategy: o
        }),
        {
            x: f,
            y: d
        } = gi(l, r, c),
        h = r,
        p = {},
        g = 0;
    for (let m = 0; m < a.length; m++) {
        const {
            name: b,
            fn: x
        } = a[m], {
            x: v,
            y,
            data: C,
            reset: $
        } = await x({
            x: f,
            y: d,
            initialPlacement: r,
            placement: h,
            strategy: o,
            middlewareData: p,
            rects: l,
            platform: i,
            elements: {
                reference: e,
                floating: t
            }
        });
        if (f = v ?? f, d = y ?? d, p = {
                ...p,
                [b]: {
                    ...p[b],
                    ...C
                }
            }, $ && g <= 50) {
            g++, typeof $ == "object" && ($.placement && (h = $.placement), $.rects && (l = $.rects === !0 ? await i.getElementRects({
                reference: e,
                floating: t,
                strategy: o
            }) : $.rects), {
                x: f,
                y: d
            } = gi(l, h, c)), m = -1;
            continue
        }
    }
    return {
        x: f,
        y: d,
        placement: h,
        strategy: o,
        middlewareData: p
    }
};
async function en(e, t) {
    var n;
    t === void 0 && (t = {});
    const {
        x: r,
        y: o,
        platform: s,
        rects: i,
        elements: a,
        strategy: c
    } = e, {
        boundary: l = "clippingAncestors",
        rootBoundary: f = "viewport",
        elementContext: d = "floating",
        altBoundary: h = !1,
        padding: p = 0
    } = Be(t, e), g = Ec(p), b = a[h ? d === "floating" ? "reference" : "floating" : d], x = Wn(await s.getClippingRect({
        element: (n = await (s.isElement == null ? void 0 : s.isElement(b))) == null || n ? b : b.contextElement || await (s.getDocumentElement == null ? void 0 : s.getDocumentElement(a.floating)),
        boundary: l,
        rootBoundary: f,
        strategy: c
    })), v = d === "floating" ? {
        ...i.floating,
        x: r,
        y: o
    } : i.reference, y = await (s.getOffsetParent == null ? void 0 : s.getOffsetParent(a.floating)), C = await (s.isElement == null ? void 0 : s.isElement(y)) ? await (s.getScale == null ? void 0 : s.getScale(y)) || {
        x: 1,
        y: 1
    } : {
        x: 1,
        y: 1
    }, $ = Wn(s.convertOffsetParentRelativeRectToViewportRelativeRect ? await s.convertOffsetParentRelativeRectToViewportRelativeRect({
        rect: v,
        offsetParent: y,
        strategy: c
    }) : v);
    return {
        top: (x.top - $.top + g.top) / C.y,
        bottom: ($.bottom - x.bottom + g.bottom) / C.y,
        left: (x.left - $.left + g.left) / C.x,
        right: ($.right - x.right + g.right) / C.x
    }
}
const vi = e => ({
        name: "arrow",
        options: e,
        async fn(t) {
            const {
                x: n,
                y: r,
                placement: o,
                rects: s,
                platform: i,
                elements: a
            } = t, {
                element: c,
                padding: l = 0
            } = Be(e, t) || {};
            if (c == null) return {};
            const f = Ec(l),
                d = {
                    x: n,
                    y: r
                },
                h = No(o),
                p = Vo(h),
                g = await i.getDimensions(c),
                m = h === "y",
                b = m ? "top" : "left",
                x = m ? "bottom" : "right",
                v = m ? "clientHeight" : "clientWidth",
                y = s.reference[p] + s.reference[h] - d[h] - s.floating[p],
                C = d[h] - s.reference[h],
                $ = await (i.getOffsetParent == null ? void 0 : i.getOffsetParent(c));
            let A = $ ? $[v] : 0;
            (!A || !await (i.isElement == null ? void 0 : i.isElement($))) && (A = a.floating[v] || s.floating[p]);
            const w = y / 2 - C / 2,
                S = A / 2 - g[p] / 2 - 1,
                E = et(f[b], S),
                j = et(f[x], S),
                I = E,
                G = A - g[p] - j,
                _ = A / 2 - g[p] / 2 + w,
                L = ro(I, _, G),
                U = It(o) != null && _ != L && s.reference[p] / 2 - (_ < I ? E : j) - g[p] / 2 < 0 ? _ < I ? I - _ : G - _ : 0;
            return {
                [h]: d[h] - U,
                data: {
                    [h]: L,
                    centerOffset: _ - L + U
                }
            }
        }
    }),
    og = function(e) {
        return e === void 0 && (e = {}), {
            name: "flip",
            options: e,
            async fn(t) {
                var n;
                const {
                    placement: r,
                    middlewareData: o,
                    rects: s,
                    initialPlacement: i,
                    platform: a,
                    elements: c
                } = t, {
                    mainAxis: l = !0,
                    crossAxis: f = !0,
                    fallbackPlacements: d,
                    fallbackStrategy: h = "bestFit",
                    fallbackAxisSideDirection: p = "none",
                    flipAlignment: g = !0,
                    ...m
                } = Be(e, t), b = Ue(r), x = Ue(i) === i, v = await (a.isRTL == null ? void 0 : a.isRTL(c.floating)), y = d || (x || !g ? [Un(i)] : Jm(i));
                !d && p !== "none" && y.push(...tg(i, g, p, v));
                const C = [i, ...y],
                    $ = await en(t, m),
                    A = [];
                let w = ((n = o.flip) == null ? void 0 : n.overflows) || [];
                if (l && A.push($[b]), f) {
                    const I = Qm(r, s, v);
                    A.push($[I[0]], $[I[1]])
                }
                if (w = [...w, {
                        placement: r,
                        overflows: A
                    }], !A.every(I => I <= 0)) {
                    var S, E;
                    const I = (((S = o.flip) == null ? void 0 : S.index) || 0) + 1,
                        G = C[I];
                    if (G) return {
                        data: {
                            index: I,
                            overflows: w
                        },
                        reset: {
                            placement: G
                        }
                    };
                    let _ = (E = w.filter(L => L.overflows[0] <= 0).sort((L, k) => L.overflows[1] - k.overflows[1])[0]) == null ? void 0 : E.placement;
                    if (!_) switch (h) {
                        case "bestFit": {
                            var j;
                            const L = (j = w.map(k => [k.placement, k.overflows.filter(U => U > 0).reduce((U, Z) => U + Z, 0)]).sort((k, U) => k[1] - U[1])[0]) == null ? void 0 : j[0];
                            L && (_ = L);
                            break
                        }
                        case "initialPlacement":
                            _ = i;
                            break
                    }
                    if (r !== _) return {
                        reset: {
                            placement: _
                        }
                    }
                }
                return {}
            }
        }
    };

function yi(e, t) {
    return {
        top: e.top - t.height,
        right: e.right - t.width,
        bottom: e.bottom - t.height,
        left: e.left - t.width
    }
}

function bi(e) {
    return Xm.some(t => e[t] >= 0)
}
const sg = function(e) {
    return e === void 0 && (e = {}), {
        name: "hide",
        options: e,
        async fn(t) {
            const {
                rects: n
            } = t, {
                strategy: r = "referenceHidden",
                ...o
            } = Be(e, t);
            switch (r) {
                case "referenceHidden": {
                    const s = await en(t, {
                            ...o,
                            elementContext: "reference"
                        }),
                        i = yi(s, n.reference);
                    return {
                        data: {
                            referenceHiddenOffsets: i,
                            referenceHidden: bi(i)
                        }
                    }
                }
                case "escaped": {
                    const s = await en(t, {
                            ...o,
                            altBoundary: !0
                        }),
                        i = yi(s, n.floating);
                    return {
                        data: {
                            escapedOffsets: i,
                            escaped: bi(i)
                        }
                    }
                }
                default:
                    return {}
            }
        }
    }
};
async function ig(e, t) {
    const {
        placement: n,
        platform: r,
        elements: o
    } = e, s = await (r.isRTL == null ? void 0 : r.isRTL(o.floating)), i = Ue(n), a = It(n), c = Vt(n) === "y", l = ["left", "top"].includes(i) ? -1 : 1, f = s && c ? -1 : 1, d = Be(t, e);
    let {
        mainAxis: h,
        crossAxis: p,
        alignmentAxis: g
    } = typeof d == "number" ? {
        mainAxis: d,
        crossAxis: 0,
        alignmentAxis: null
    } : {
        mainAxis: 0,
        crossAxis: 0,
        alignmentAxis: null,
        ...d
    };
    return a && typeof g == "number" && (p = a === "end" ? g * -1 : g), c ? {
        x: p * f,
        y: h * l
    } : {
        x: h * l,
        y: p * f
    }
}
const ag = function(e) {
        return e === void 0 && (e = 0), {
            name: "offset",
            options: e,
            async fn(t) {
                const {
                    x: n,
                    y: r
                } = t, o = await ig(t, e);
                return {
                    x: n + o.x,
                    y: r + o.y,
                    data: o
                }
            }
        }
    },
    cg = function(e) {
        return e === void 0 && (e = {}), {
            name: "shift",
            options: e,
            async fn(t) {
                const {
                    x: n,
                    y: r,
                    placement: o
                } = t, {
                    mainAxis: s = !0,
                    crossAxis: i = !1,
                    limiter: a = {
                        fn: b => {
                            let {
                                x,
                                y: v
                            } = b;
                            return {
                                x,
                                y: v
                            }
                        }
                    },
                    ...c
                } = Be(e, t), l = {
                    x: n,
                    y: r
                }, f = await en(t, c), d = Vt(Ue(o)), h = Io(d);
                let p = l[h],
                    g = l[d];
                if (s) {
                    const b = h === "y" ? "top" : "left",
                        x = h === "y" ? "bottom" : "right",
                        v = p + f[b],
                        y = p - f[x];
                    p = ro(v, p, y)
                }
                if (i) {
                    const b = d === "y" ? "top" : "left",
                        x = d === "y" ? "bottom" : "right",
                        v = g + f[b],
                        y = g - f[x];
                    g = ro(v, g, y)
                }
                const m = a.fn({
                    ...t,
                    [h]: p,
                    [d]: g
                });
                return {
                    ...m,
                    data: {
                        x: m.x - n,
                        y: m.y - r
                    }
                }
            }
        }
    },
    lg = function(e) {
        return e === void 0 && (e = {}), {
            options: e,
            fn(t) {
                const {
                    x: n,
                    y: r,
                    placement: o,
                    rects: s,
                    middlewareData: i
                } = t, {
                    offset: a = 0,
                    mainAxis: c = !0,
                    crossAxis: l = !0
                } = Be(e, t), f = {
                    x: n,
                    y: r
                }, d = Vt(o), h = Io(d);
                let p = f[h],
                    g = f[d];
                const m = Be(a, t),
                    b = typeof m == "number" ? {
                        mainAxis: m,
                        crossAxis: 0
                    } : {
                        mainAxis: 0,
                        crossAxis: 0,
                        ...m
                    };
                if (c) {
                    const y = h === "y" ? "height" : "width",
                        C = s.reference[h] - s.floating[y] + b.mainAxis,
                        $ = s.reference[h] + s.reference[y] - b.mainAxis;
                    p < C ? p = C : p > $ && (p = $)
                }
                if (l) {
                    var x, v;
                    const y = h === "y" ? "width" : "height",
                        C = ["top", "left"].includes(Ue(o)),
                        $ = s.reference[d] - s.floating[y] + (C && ((x = i.offset) == null ? void 0 : x[d]) || 0) + (C ? 0 : b.crossAxis),
                        A = s.reference[d] + s.reference[y] + (C ? 0 : ((v = i.offset) == null ? void 0 : v[d]) || 0) - (C ? b.crossAxis : 0);
                    g < $ ? g = $ : g > A && (g = A)
                }
                return {
                    [h]: p,
                    [d]: g
                }
            }
        }
    },
    ug = function(e) {
        return e === void 0 && (e = {}), {
            name: "size",
            options: e,
            async fn(t) {
                const {
                    placement: n,
                    rects: r,
                    platform: o,
                    elements: s
                } = t, {
                    apply: i = () => {},
                    ...a
                } = Be(e, t), c = await en(t, a), l = Ue(n), f = It(n), d = Vt(n) === "y", {
                    width: h,
                    height: p
                } = r.floating;
                let g, m;
                l === "top" || l === "bottom" ? (g = l, m = f === (await (o.isRTL == null ? void 0 : o.isRTL(s.floating)) ? "start" : "end") ? "left" : "right") : (m = l, g = f === "end" ? "top" : "bottom");
                const b = p - c[g],
                    x = h - c[m],
                    v = !t.middlewareData.shift;
                let y = b,
                    C = x;
                if (d) {
                    const A = h - c.left - c.right;
                    C = f || v ? et(x, A) : A
                } else {
                    const A = p - c.top - c.bottom;
                    y = f || v ? et(b, A) : A
                }
                if (v && !f) {
                    const A = ge(c.left, 0),
                        w = ge(c.right, 0),
                        S = ge(c.top, 0),
                        E = ge(c.bottom, 0);
                    d ? C = h - 2 * (A !== 0 || w !== 0 ? A + w : ge(c.left, c.right)) : y = p - 2 * (S !== 0 || E !== 0 ? S + E : ge(c.top, c.bottom))
                }
                await i({
                    ...t,
                    availableWidth: C,
                    availableHeight: y
                });
                const $ = await o.getDimensions(s.floating);
                return h !== $.width || p !== $.height ? {
                    reset: {
                        rects: !0
                    }
                } : {}
            }
        }
    };

function nt(e) {
    return Tc(e) ? (e.nodeName || "").toLowerCase() : "#document"
}

function ye(e) {
    var t;
    return (e == null || (t = e.ownerDocument) == null ? void 0 : t.defaultView) || window
}

function He(e) {
    var t;
    return (t = (Tc(e) ? e.ownerDocument : e.document) || window.document) == null ? void 0 : t.documentElement
}

function Tc(e) {
    return e instanceof Node || e instanceof ye(e).Node
}

function We(e) {
    return e instanceof Element || e instanceof ye(e).Element
}

function _e(e) {
    return e instanceof HTMLElement || e instanceof ye(e).HTMLElement
}

function xi(e) {
    return typeof ShadowRoot > "u" ? !1 : e instanceof ShadowRoot || e instanceof ye(e).ShadowRoot
}

function un(e) {
    const {
        overflow: t,
        overflowX: n,
        overflowY: r,
        display: o
    } = Ae(e);
    return /auto|scroll|overlay|hidden|clip/.test(t + r + n) && !["inline", "contents"].includes(o)
}

function fg(e) {
    return ["table", "td", "th"].includes(nt(e))
}

function Fo(e) {
    const t = jo(),
        n = Ae(e);
    return n.transform !== "none" || n.perspective !== "none" || (n.containerType ? n.containerType !== "normal" : !1) || !t && (n.backdropFilter ? n.backdropFilter !== "none" : !1) || !t && (n.filter ? n.filter !== "none" : !1) || ["transform", "perspective", "filter"].some(r => (n.willChange || "").includes(r)) || ["paint", "layout", "strict", "content"].some(r => (n.contain || "").includes(r))
}

function dg(e) {
    let t = kt(e);
    for (; _e(t) && !ar(t);) {
        if (Fo(t)) return t;
        t = kt(t)
    }
    return null
}

function jo() {
    return typeof CSS > "u" || !CSS.supports ? !1 : CSS.supports("-webkit-backdrop-filter", "none")
}

function ar(e) {
    return ["html", "body", "#document"].includes(nt(e))
}

function Ae(e) {
    return ye(e).getComputedStyle(e)
}

function cr(e) {
    return We(e) ? {
        scrollLeft: e.scrollLeft,
        scrollTop: e.scrollTop
    } : {
        scrollLeft: e.pageXOffset,
        scrollTop: e.pageYOffset
    }
}

function kt(e) {
    if (nt(e) === "html") return e;
    const t = e.assignedSlot || e.parentNode || xi(e) && e.host || He(e);
    return xi(t) ? t.host : t
}

function Mc(e) {
    const t = kt(e);
    return ar(t) ? e.ownerDocument ? e.ownerDocument.body : e.body : _e(t) && un(t) ? t : Mc(t)
}

function Hn(e, t) {
    var n;
    t === void 0 && (t = []);
    const r = Mc(e),
        o = r === ((n = e.ownerDocument) == null ? void 0 : n.body),
        s = ye(r);
    return o ? t.concat(s, s.visualViewport || [], un(r) ? r : []) : t.concat(r, Hn(r))
}

function Dc(e) {
    const t = Ae(e);
    let n = parseFloat(t.width) || 0,
        r = parseFloat(t.height) || 0;
    const o = _e(e),
        s = o ? e.offsetWidth : n,
        i = o ? e.offsetHeight : r,
        a = Bn(n) !== s || Bn(r) !== i;
    return a && (n = s, r = i), {
        width: n,
        height: r,
        $: a
    }
}

function Bo(e) {
    return We(e) ? e : e.contextElement
}

function Et(e) {
    const t = Bo(e);
    if (!_e(t)) return tt(1);
    const n = t.getBoundingClientRect(),
        {
            width: r,
            height: o,
            $: s
        } = Dc(t);
    let i = (s ? Bn(n.width) : n.width) / r,
        a = (s ? Bn(n.height) : n.height) / o;
    return (!i || !Number.isFinite(i)) && (i = 1), (!a || !Number.isFinite(a)) && (a = 1), {
        x: i,
        y: a
    }
}
const hg = tt(0);

function Rc(e) {
    const t = ye(e);
    return !jo() || !t.visualViewport ? hg : {
        x: t.visualViewport.offsetLeft,
        y: t.visualViewport.offsetTop
    }
}

function pg(e, t, n) {
    return t === void 0 && (t = !1), !n || t && n !== ye(e) ? !1 : t
}

function ft(e, t, n, r) {
    t === void 0 && (t = !1), n === void 0 && (n = !1);
    const o = e.getBoundingClientRect(),
        s = Bo(e);
    let i = tt(1);
    t && (r ? We(r) && (i = Et(r)) : i = Et(e));
    const a = pg(s, n, r) ? Rc(s) : tt(0);
    let c = (o.left + a.x) / i.x,
        l = (o.top + a.y) / i.y,
        f = o.width / i.x,
        d = o.height / i.y;
    if (s) {
        const h = ye(s),
            p = r && We(r) ? ye(r) : r;
        let g = h.frameElement;
        for (; g && r && p !== h;) {
            const m = Et(g),
                b = g.getBoundingClientRect(),
                x = Ae(g),
                v = b.left + (g.clientLeft + parseFloat(x.paddingLeft)) * m.x,
                y = b.top + (g.clientTop + parseFloat(x.paddingTop)) * m.y;
            c *= m.x, l *= m.y, f *= m.x, d *= m.y, c += v, l += y, g = ye(g).frameElement
        }
    }
    return Wn({
        width: f,
        height: d,
        x: c,
        y: l
    })
}

function mg(e) {
    let {
        rect: t,
        offsetParent: n,
        strategy: r
    } = e;
    const o = _e(n),
        s = He(n);
    if (n === s) return t;
    let i = {
            scrollLeft: 0,
            scrollTop: 0
        },
        a = tt(1);
    const c = tt(0);
    if ((o || !o && r !== "fixed") && ((nt(n) !== "body" || un(s)) && (i = cr(n)), _e(n))) {
        const l = ft(n);
        a = Et(n), c.x = l.x + n.clientLeft, c.y = l.y + n.clientTop
    }
    return {
        width: t.width * a.x,
        height: t.height * a.y,
        x: t.x * a.x - i.scrollLeft * a.x + c.x,
        y: t.y * a.y - i.scrollTop * a.y + c.y
    }
}

function gg(e) {
    return Array.from(e.getClientRects())
}

function Oc(e) {
    return ft(He(e)).left + cr(e).scrollLeft
}

function vg(e) {
    const t = He(e),
        n = cr(e),
        r = e.ownerDocument.body,
        o = ge(t.scrollWidth, t.clientWidth, r.scrollWidth, r.clientWidth),
        s = ge(t.scrollHeight, t.clientHeight, r.scrollHeight, r.clientHeight);
    let i = -n.scrollLeft + Oc(e);
    const a = -n.scrollTop;
    return Ae(r).direction === "rtl" && (i += ge(t.clientWidth, r.clientWidth) - o), {
        width: o,
        height: s,
        x: i,
        y: a
    }
}

function yg(e, t) {
    const n = ye(e),
        r = He(e),
        o = n.visualViewport;
    let s = r.clientWidth,
        i = r.clientHeight,
        a = 0,
        c = 0;
    if (o) {
        s = o.width, i = o.height;
        const l = jo();
        (!l || l && t === "fixed") && (a = o.offsetLeft, c = o.offsetTop)
    }
    return {
        width: s,
        height: i,
        x: a,
        y: c
    }
}

function bg(e, t) {
    const n = ft(e, !0, t === "fixed"),
        r = n.top + e.clientTop,
        o = n.left + e.clientLeft,
        s = _e(e) ? Et(e) : tt(1),
        i = e.clientWidth * s.x,
        a = e.clientHeight * s.y,
        c = o * s.x,
        l = r * s.y;
    return {
        width: i,
        height: a,
        x: c,
        y: l
    }
}

function wi(e, t, n) {
    let r;
    if (t === "viewport") r = yg(e, n);
    else if (t === "document") r = vg(He(e));
    else if (We(t)) r = bg(t, n);
    else {
        const o = Rc(e);
        r = {
            ...t,
            x: t.x - o.x,
            y: t.y - o.y
        }
    }
    return Wn(r)
}

function _c(e, t) {
    const n = kt(e);
    return n === t || !We(n) || ar(n) ? !1 : Ae(n).position === "fixed" || _c(n, t)
}

function xg(e, t) {
    const n = t.get(e);
    if (n) return n;
    let r = Hn(e).filter(a => We(a) && nt(a) !== "body"),
        o = null;
    const s = Ae(e).position === "fixed";
    let i = s ? kt(e) : e;
    for (; We(i) && !ar(i);) {
        const a = Ae(i),
            c = Fo(i);
        !c && a.position === "fixed" && (o = null), (s ? !c && !o : !c && a.position === "static" && !!o && ["absolute", "fixed"].includes(o.position) || un(i) && !c && _c(e, i)) ? r = r.filter(f => f !== i) : o = a, i = kt(i)
    }
    return t.set(e, r), r
}

function wg(e) {
    let {
        element: t,
        boundary: n,
        rootBoundary: r,
        strategy: o
    } = e;
    const i = [...n === "clippingAncestors" ? xg(t, this._c) : [].concat(n), r],
        a = i[0],
        c = i.reduce((l, f) => {
            const d = wi(t, f, o);
            return l.top = ge(d.top, l.top), l.right = et(d.right, l.right), l.bottom = et(d.bottom, l.bottom), l.left = ge(d.left, l.left), l
        }, wi(t, a, o));
    return {
        width: c.right - c.left,
        height: c.bottom - c.top,
        x: c.left,
        y: c.top
    }
}

function Cg(e) {
    return Dc(e)
}

function Ag(e, t, n) {
    const r = _e(t),
        o = He(t),
        s = n === "fixed",
        i = ft(e, !0, s, t);
    let a = {
        scrollLeft: 0,
        scrollTop: 0
    };
    const c = tt(0);
    if (r || !r && !s)
        if ((nt(t) !== "body" || un(o)) && (a = cr(t)), r) {
            const l = ft(t, !0, s, t);
            c.x = l.x + t.clientLeft, c.y = l.y + t.clientTop
        } else o && (c.x = Oc(o));
    return {
        x: i.left + a.scrollLeft - c.x,
        y: i.top + a.scrollTop - c.y,
        width: i.width,
        height: i.height
    }
}

function Ci(e, t) {
    return !_e(e) || Ae(e).position === "fixed" ? null : t ? t(e) : e.offsetParent
}

function kc(e, t) {
    const n = ye(e);
    if (!_e(e)) return n;
    let r = Ci(e, t);
    for (; r && fg(r) && Ae(r).position === "static";) r = Ci(r, t);
    return r && (nt(r) === "html" || nt(r) === "body" && Ae(r).position === "static" && !Fo(r)) ? n : r || dg(e) || n
}
const $g = async function(e) {
    let {
        reference: t,
        floating: n,
        strategy: r
    } = e;
    const o = this.getOffsetParent || kc,
        s = this.getDimensions;
    return {
        reference: Ag(t, await o(n), r),
        floating: {
            x: 0,
            y: 0,
            ...await s(n)
        }
    }
};

function Pg(e) {
    return Ae(e).direction === "rtl"
}
const Sg = {
    convertOffsetParentRelativeRectToViewportRelativeRect: mg,
    getDocumentElement: He,
    getClippingRect: wg,
    getOffsetParent: kc,
    getElementRects: $g,
    getClientRects: gg,
    getDimensions: Cg,
    getScale: Et,
    isElement: We,
    isRTL: Pg
};

function Eg(e, t) {
    let n = null,
        r;
    const o = He(e);

    function s() {
        clearTimeout(r), n && n.disconnect(), n = null
    }

    function i(a, c) {
        a === void 0 && (a = !1), c === void 0 && (c = 1), s();
        const {
            left: l,
            top: f,
            width: d,
            height: h
        } = e.getBoundingClientRect();
        if (a || t(), !d || !h) return;
        const p = yn(f),
            g = yn(o.clientWidth - (l + d)),
            m = yn(o.clientHeight - (f + h)),
            b = yn(l),
            v = {
                rootMargin: -p + "px " + -g + "px " + -m + "px " + -b + "px",
                threshold: ge(0, et(1, c)) || 1
            };
        let y = !0;

        function C($) {
            const A = $[0].intersectionRatio;
            if (A !== c) {
                if (!y) return i();
                A ? i(!1, A) : r = setTimeout(() => {
                    i(!1, 1e-7)
                }, 100)
            }
            y = !1
        }
        try {
            n = new IntersectionObserver(C, {
                ...v,
                root: o.ownerDocument
            })
        } catch {
            n = new IntersectionObserver(C, v)
        }
        n.observe(e)
    }
    return i(!0), s
}

function Tg(e, t, n, r) {
    r === void 0 && (r = {});
    const {
        ancestorScroll: o = !0,
        ancestorResize: s = !0,
        elementResize: i = typeof ResizeObserver == "function",
        layoutShift: a = typeof IntersectionObserver == "function",
        animationFrame: c = !1
    } = r, l = Bo(e), f = o || s ? [...l ? Hn(l) : [], ...Hn(t)] : [];
    f.forEach(x => {
        o && x.addEventListener("scroll", n, {
            passive: !0
        }), s && x.addEventListener("resize", n)
    });
    const d = l && a ? Eg(l, n) : null;
    let h = -1,
        p = null;
    i && (p = new ResizeObserver(x => {
        let [v] = x;
        v && v.target === l && p && (p.unobserve(t), cancelAnimationFrame(h), h = requestAnimationFrame(() => {
            p && p.observe(t)
        })), n()
    }), l && !c && p.observe(l), p.observe(t));
    let g, m = c ? ft(e) : null;
    c && b();

    function b() {
        const x = ft(e);
        m && (x.x !== m.x || x.y !== m.y || x.width !== m.width || x.height !== m.height) && n(), m = x, g = requestAnimationFrame(b)
    }
    return n(), () => {
        f.forEach(x => {
            o && x.removeEventListener("scroll", n), s && x.removeEventListener("resize", n)
        }), d && d(), p && p.disconnect(), p = null, c && cancelAnimationFrame(g)
    }
}
const Mg = (e, t, n) => {
        const r = new Map,
            o = {
                platform: Sg,
                ...n
            },
            s = {
                ...o.platform,
                _c: r
            };
        return rg(e, t, {
            ...o,
            platform: s
        })
    },
    Dg = e => {
        function t(n) {
            return {}.hasOwnProperty.call(n, "current")
        }
        return {
            name: "arrow",
            options: e,
            fn(n) {
                const {
                    element: r,
                    padding: o
                } = typeof e == "function" ? e(n) : e;
                return r && t(r) ? r.current != null ? vi({
                    element: r.current,
                    padding: o
                }).fn(n) : {} : r ? vi({
                    element: r,
                    padding: o
                }).fn(n) : {}
            }
        }
    };
var En = typeof document < "u" ? u.useLayoutEffect : u.useEffect;

function Gn(e, t) {
    if (e === t) return !0;
    if (typeof e != typeof t) return !1;
    if (typeof e == "function" && e.toString() === t.toString()) return !0;
    let n, r, o;
    if (e && t && typeof e == "object") {
        if (Array.isArray(e)) {
            if (n = e.length, n != t.length) return !1;
            for (r = n; r-- !== 0;)
                if (!Gn(e[r], t[r])) return !1;
            return !0
        }
        if (o = Object.keys(e), n = o.length, n !== Object.keys(t).length) return !1;
        for (r = n; r-- !== 0;)
            if (!{}.hasOwnProperty.call(t, o[r])) return !1;
        for (r = n; r-- !== 0;) {
            const s = o[r];
            if (!(s === "_owner" && e.$$typeof) && !Gn(e[s], t[s])) return !1
        }
        return !0
    }
    return e !== e && t !== t
}

function Lc(e) {
    return typeof window > "u" ? 1 : (e.ownerDocument.defaultView || window).devicePixelRatio || 1
}

function Ai(e, t) {
    const n = Lc(e);
    return Math.round(t * n) / n
}

function $i(e) {
    const t = u.useRef(e);
    return En(() => {
        t.current = e
    }), t
}

function Rg(e) {
    e === void 0 && (e = {});
    const {
        placement: t = "bottom",
        strategy: n = "absolute",
        middleware: r = [],
        platform: o,
        elements: {
            reference: s,
            floating: i
        } = {},
        transform: a = !0,
        whileElementsMounted: c,
        open: l
    } = e, [f, d] = u.useState({
        x: 0,
        y: 0,
        strategy: n,
        placement: t,
        middlewareData: {},
        isPositioned: !1
    }), [h, p] = u.useState(r);
    Gn(h, r) || p(r);
    const [g, m] = u.useState(null), [b, x] = u.useState(null), v = u.useCallback(U => {
        U != A.current && (A.current = U, m(U))
    }, [m]), y = u.useCallback(U => {
        U !== w.current && (w.current = U, x(U))
    }, [x]), C = s || g, $ = i || b, A = u.useRef(null), w = u.useRef(null), S = u.useRef(f), E = $i(c), j = $i(o), I = u.useCallback(() => {
        if (!A.current || !w.current) return;
        const U = {
            placement: t,
            strategy: n,
            middleware: h
        };
        j.current && (U.platform = j.current), Mg(A.current, w.current, U).then(Z => {
            const q = {
                ...Z,
                isPositioned: !0
            };
            G.current && !Gn(S.current, q) && (S.current = q, on.flushSync(() => {
                d(q)
            }))
        })
    }, [h, t, n, j]);
    En(() => {
        l === !1 && S.current.isPositioned && (S.current.isPositioned = !1, d(U => ({
            ...U,
            isPositioned: !1
        })))
    }, [l]);
    const G = u.useRef(!1);
    En(() => (G.current = !0, () => {
        G.current = !1
    }), []), En(() => {
        if (C && (A.current = C), $ && (w.current = $), C && $) {
            if (E.current) return E.current(C, $, I);
            I()
        }
    }, [C, $, I, E]);
    const _ = u.useMemo(() => ({
            reference: A,
            floating: w,
            setReference: v,
            setFloating: y
        }), [v, y]),
        L = u.useMemo(() => ({
            reference: C,
            floating: $
        }), [C, $]),
        k = u.useMemo(() => {
            const U = {
                position: n,
                left: 0,
                top: 0
            };
            if (!L.floating) return U;
            const Z = Ai(L.floating, f.x),
                q = Ai(L.floating, f.y);
            return a ? {
                ...U,
                transform: "translate(" + Z + "px, " + q + "px)",
                ...Lc(L.floating) >= 1.5 && {
                    willChange: "transform"
                }
            } : {
                position: n,
                left: Z,
                top: q
            }
        }, [n, a, L.floating, f.x, f.y]);
    return u.useMemo(() => ({
        ...f,
        update: I,
        refs: _,
        elements: L,
        floatingStyles: k
    }), [f, I, _, L, k])
}

function Og(e) {
    const [t, n] = u.useState(void 0);
    return Ot(() => {
        if (e) {
            n({
                width: e.offsetWidth,
                height: e.offsetHeight
            });
            const r = new ResizeObserver(o => {
                if (!Array.isArray(o) || !o.length) return;
                const s = o[0];
                let i, a;
                if ("borderBoxSize" in s) {
                    const c = s.borderBoxSize,
                        l = Array.isArray(c) ? c[0] : c;
                    i = l.inlineSize, a = l.blockSize
                } else i = e.offsetWidth, a = e.offsetHeight;
                n({
                    width: i,
                    height: a
                })
            });
            return r.observe(e, {
                box: "border-box"
            }), () => r.unobserve(e)
        } else n(void 0)
    }, [e]), t
}
const Ic = "Popper",
    [Vc, lr] = Lt(Ic),
    [_g, Nc] = Vc(Ic),
    kg = e => {
        const {
            __scopePopper: t,
            children: n
        } = e, [r, o] = u.useState(null);
        return u.createElement(_g, {
            scope: t,
            anchor: r,
            onAnchorChange: o
        }, n)
    },
    Lg = "PopperAnchor",
    Ig = u.forwardRef((e, t) => {
        const {
            __scopePopper: n,
            virtualRef: r,
            ...o
        } = e, s = Nc(Lg, n), i = u.useRef(null), a = le(t, i);
        return u.useEffect(() => {
            s.onAnchorChange(r?.current || i.current)
        }), r ? null : u.createElement(ue.div, V({}, o, {
            ref: a
        }))
    }),
    Fc = "PopperContent",
    [Vg, ey] = Vc(Fc),
    Ng = u.forwardRef((e, t) => {
        var n, r, o, s, i, a, c, l;
        const {
            __scopePopper: f,
            side: d = "bottom",
            sideOffset: h = 0,
            align: p = "center",
            alignOffset: g = 0,
            arrowPadding: m = 0,
            collisionBoundary: b = [],
            collisionPadding: x = 0,
            sticky: v = "partial",
            hideWhenDetached: y = !1,
            avoidCollisions: C = !0,
            onPlaced: $,
            ...A
        } = e, w = Nc(Fc, f), [S, E] = u.useState(null), j = le(t, Se => E(Se)), [I, G] = u.useState(null), _ = Og(I), L = (n = _?.width) !== null && n !== void 0 ? n : 0, k = (r = _?.height) !== null && r !== void 0 ? r : 0, U = d + (p !== "center" ? "-" + p : ""), Z = typeof x == "number" ? x : {
            top: 0,
            right: 0,
            bottom: 0,
            left: 0,
            ...x
        }, q = Array.isArray(b) ? b : [b], N = q.length > 0, Y = {
            padding: Z,
            boundary: q.filter(Fg),
            altBoundary: N
        }, {
            refs: W,
            floatingStyles: P,
            placement: T,
            isPositioned: R,
            middlewareData: D
        } = Rg({
            strategy: "fixed",
            placement: U,
            whileElementsMounted: Tg,
            elements: {
                reference: w.anchor
            },
            middleware: [ag({
                mainAxis: h + k,
                alignmentAxis: g
            }), C && cg({
                mainAxis: !0,
                crossAxis: !1,
                limiter: v === "partial" ? lg() : void 0,
                ...Y
            }), C && og({
                ...Y
            }), ug({
                ...Y,
                apply: ({
                    elements: Se,
                    rects: Ft,
                    availableWidth: gt,
                    availableHeight: vt
                }) => {
                    const {
                        width: mr,
                        height: dn
                    } = Ft.reference, it = Se.floating.style;
                    it.setProperty("--radix-popper-available-width", `${gt}px`), it.setProperty("--radix-popper-available-height", `${vt}px`), it.setProperty("--radix-popper-anchor-width", `${mr}px`), it.setProperty("--radix-popper-anchor-height", `${dn}px`)
                }
            }), I && Dg({
                element: I,
                padding: m
            }), jg({
                arrowWidth: L,
                arrowHeight: k
            }), y && sg({
                strategy: "referenceHidden"
            })]
        }), [O, F] = jc(T), H = Ce($);
        Ot(() => {
            R && H?.()
        }, [R, H]);
        const z = (o = D.arrow) === null || o === void 0 ? void 0 : o.x,
            X = (s = D.arrow) === null || s === void 0 ? void 0 : s.y,
            ae = ((i = D.arrow) === null || i === void 0 ? void 0 : i.centerOffset) !== 0,
            [$e, Pe] = u.useState();
        return Ot(() => {
            S && Pe(window.getComputedStyle(S).zIndex)
        }, [S]), u.createElement("div", {
            ref: W.setFloating,
            "data-radix-popper-content-wrapper": "",
            style: {
                ...P,
                transform: R ? P.transform : "translate(0, -200%)",
                minWidth: "max-content",
                zIndex: $e,
                ["--radix-popper-transform-origin"]: [(a = D.transformOrigin) === null || a === void 0 ? void 0 : a.x, (c = D.transformOrigin) === null || c === void 0 ? void 0 : c.y].join(" ")
            },
            dir: e.dir
        }, u.createElement(Vg, {
            scope: f,
            placedSide: O,
            onArrowChange: G,
            arrowX: z,
            arrowY: X,
            shouldHideArrow: ae
        }, u.createElement(ue.div, V({
            "data-side": O,
            "data-align": F
        }, A, {
            ref: j,
            style: {
                ...A.style,
                animation: R ? void 0 : "none",
                opacity: (l = D.hide) !== null && l !== void 0 && l.referenceHidden ? 0 : void 0
            }
        }))))
    });

function Fg(e) {
    return e !== null
}
const jg = e => ({
    name: "transformOrigin",
    options: e,
    fn(t) {
        var n, r, o, s, i;
        const {
            placement: a,
            rects: c,
            middlewareData: l
        } = t, d = ((n = l.arrow) === null || n === void 0 ? void 0 : n.centerOffset) !== 0, h = d ? 0 : e.arrowWidth, p = d ? 0 : e.arrowHeight, [g, m] = jc(a), b = {
            start: "0%",
            center: "50%",
            end: "100%"
        } [m], x = ((r = (o = l.arrow) === null || o === void 0 ? void 0 : o.x) !== null && r !== void 0 ? r : 0) + h / 2, v = ((s = (i = l.arrow) === null || i === void 0 ? void 0 : i.y) !== null && s !== void 0 ? s : 0) + p / 2;
        let y = "",
            C = "";
        return g === "bottom" ? (y = d ? b : `${x}px`, C = `${-p}px`) : g === "top" ? (y = d ? b : `${x}px`, C = `${c.floating.height+p}px`) : g === "right" ? (y = `${-p}px`, C = d ? b : `${v}px`) : g === "left" && (y = `${c.floating.width+p}px`, C = d ? b : `${v}px`), {
            data: {
                x: y,
                y: C
            }
        }
    }
});

function jc(e) {
    const [t, n = "center"] = e.split("-");
    return [t, n]
}
const Uo = kg,
    Bc = Ig,
    Uc = Ng,
    Bg = u.forwardRef((e, t) => {
        var n;
        const {
            container: r = globalThis == null || (n = globalThis.document) === null || n === void 0 ? void 0 : n.body,
            ...o
        } = e;
        return r ? _l.createPortal(u.createElement(ue.div, V({}, o, {
            ref: t
        })), r) : null
    });

function Ug(e, t) {
    return u.useReducer((n, r) => {
        const o = t[n][r];
        return o ?? n
    }, e)
}
const Nt = e => {
    const {
        present: t,
        children: n
    } = e, r = Wg(t), o = typeof n == "function" ? n({
        present: r.isPresent
    }) : u.Children.only(n), s = le(r.ref, o.ref);
    return typeof n == "function" || r.isPresent ? u.cloneElement(o, {
        ref: s
    }) : null
};
Nt.displayName = "Presence";

function Wg(e) {
    const [t, n] = u.useState(), r = u.useRef({}), o = u.useRef(e), s = u.useRef("none"), i = e ? "mounted" : "unmounted", [a, c] = Ug(i, {
        mounted: {
            UNMOUNT: "unmounted",
            ANIMATION_OUT: "unmountSuspended"
        },
        unmountSuspended: {
            MOUNT: "mounted",
            ANIMATION_END: "unmounted"
        },
        unmounted: {
            MOUNT: "mounted"
        }
    });
    return u.useEffect(() => {
        const l = bn(r.current);
        s.current = a === "mounted" ? l : "none"
    }, [a]), Ot(() => {
        const l = r.current,
            f = o.current;
        if (f !== e) {
            const h = s.current,
                p = bn(l);
            e ? c("MOUNT") : p === "none" || l?.display === "none" ? c("UNMOUNT") : c(f && h !== p ? "ANIMATION_OUT" : "UNMOUNT"), o.current = e
        }
    }, [e, c]), Ot(() => {
        if (t) {
            const l = d => {
                    const p = bn(r.current).includes(d.animationName);
                    d.target === t && p && on.flushSync(() => c("ANIMATION_END"))
                },
                f = d => {
                    d.target === t && (s.current = bn(r.current))
                };
            return t.addEventListener("animationstart", f), t.addEventListener("animationcancel", l), t.addEventListener("animationend", l), () => {
                t.removeEventListener("animationstart", f), t.removeEventListener("animationcancel", l), t.removeEventListener("animationend", l)
            }
        } else c("ANIMATION_END")
    }, [t, c]), {
        isPresent: ["mounted", "unmountSuspended"].includes(a),
        ref: u.useCallback(l => {
            l && (r.current = getComputedStyle(l)), n(l)
        }, [])
    }
}

function bn(e) {
    return e?.animationName || "none"
}
const Lr = "rovingFocusGroup.onEntryFocus",
    Hg = {
        bubbles: !1,
        cancelable: !0
    },
    Wo = "RovingFocusGroup",
    [so, Wc, Gg] = Cc(Wo),
    [Kg, Hc] = Lt(Wo, [Gg]),
    [zg, Yg] = Kg(Wo),
    Xg = u.forwardRef((e, t) => u.createElement(so.Provider, {
        scope: e.__scopeRovingFocusGroup
    }, u.createElement(so.Slot, {
        scope: e.__scopeRovingFocusGroup
    }, u.createElement(Zg, V({}, e, {
        ref: t
    }))))),
    Zg = u.forwardRef((e, t) => {
        const {
            __scopeRovingFocusGroup: n,
            orientation: r,
            loop: o = !1,
            dir: s,
            currentTabStopId: i,
            defaultCurrentTabStopId: a,
            onCurrentTabStopIdChange: c,
            onEntryFocus: l,
            ...f
        } = e, d = u.useRef(null), h = le(t, d), p = Ac(s), [g = null, m] = ir({
            prop: i,
            defaultProp: a,
            onChange: c
        }), [b, x] = u.useState(!1), v = Ce(l), y = Wc(n), C = u.useRef(!1), [$, A] = u.useState(0);
        return u.useEffect(() => {
            const w = d.current;
            if (w) return w.addEventListener(Lr, v), () => w.removeEventListener(Lr, v)
        }, [v]), u.createElement(zg, {
            scope: n,
            orientation: r,
            dir: p,
            loop: o,
            currentTabStopId: g,
            onItemFocus: u.useCallback(w => m(w), [m]),
            onItemShiftTab: u.useCallback(() => x(!0), []),
            onFocusableItemAdd: u.useCallback(() => A(w => w + 1), []),
            onFocusableItemRemove: u.useCallback(() => A(w => w - 1), [])
        }, u.createElement(ue.div, V({
            tabIndex: b || $ === 0 ? -1 : 0,
            "data-orientation": r
        }, f, {
            ref: h,
            style: {
                outline: "none",
                ...e.style
            },
            onMouseDown: K(e.onMouseDown, () => {
                C.current = !0
            }),
            onFocus: K(e.onFocus, w => {
                const S = !C.current;
                if (w.target === w.currentTarget && S && !b) {
                    const E = new CustomEvent(Lr, Hg);
                    if (w.currentTarget.dispatchEvent(E), !E.defaultPrevented) {
                        const j = y().filter(k => k.focusable),
                            I = j.find(k => k.active),
                            G = j.find(k => k.id === g),
                            L = [I, G, ...j].filter(Boolean).map(k => k.ref.current);
                        Gc(L)
                    }
                }
                C.current = !1
            }),
            onBlur: K(e.onBlur, () => x(!1))
        })))
    }),
    qg = "RovingFocusGroupItem",
    Qg = u.forwardRef((e, t) => {
        const {
            __scopeRovingFocusGroup: n,
            focusable: r = !0,
            active: o = !1,
            tabStopId: s,
            ...i
        } = e, a = _t(), c = s || a, l = Yg(qg, n), f = l.currentTabStopId === c, d = Wc(n), {
            onFocusableItemAdd: h,
            onFocusableItemRemove: p
        } = l;
        return u.useEffect(() => {
            if (r) return h(), () => p()
        }, [r, h, p]), u.createElement(so.ItemSlot, {
            scope: n,
            id: c,
            focusable: r,
            active: o
        }, u.createElement(ue.span, V({
            tabIndex: f ? 0 : -1,
            "data-orientation": l.orientation
        }, i, {
            ref: t,
            onMouseDown: K(e.onMouseDown, g => {
                r ? l.onItemFocus(c) : g.preventDefault()
            }),
            onFocus: K(e.onFocus, () => l.onItemFocus(c)),
            onKeyDown: K(e.onKeyDown, g => {
                if (g.key === "Tab" && g.shiftKey) {
                    l.onItemShiftTab();
                    return
                }
                if (g.target !== g.currentTarget) return;
                const m = t0(g, l.orientation, l.dir);
                if (m !== void 0) {
                    g.preventDefault();
                    let x = d().filter(v => v.focusable).map(v => v.ref.current);
                    if (m === "last") x.reverse();
                    else if (m === "prev" || m === "next") {
                        m === "prev" && x.reverse();
                        const v = x.indexOf(g.currentTarget);
                        x = l.loop ? n0(x, v + 1) : x.slice(v + 1)
                    }
                    setTimeout(() => Gc(x))
                }
            })
        })))
    }),
    Jg = {
        ArrowLeft: "prev",
        ArrowUp: "prev",
        ArrowRight: "next",
        ArrowDown: "next",
        PageUp: "first",
        Home: "first",
        PageDown: "last",
        End: "last"
    };

function e0(e, t) {
    return t !== "rtl" ? e : e === "ArrowLeft" ? "ArrowRight" : e === "ArrowRight" ? "ArrowLeft" : e
}

function t0(e, t, n) {
    const r = e0(e.key, n);
    if (!(t === "vertical" && ["ArrowLeft", "ArrowRight"].includes(r)) && !(t === "horizontal" && ["ArrowUp", "ArrowDown"].includes(r))) return Jg[r]
}

function Gc(e) {
    const t = document.activeElement;
    for (const n of e)
        if (n === t || (n.focus(), document.activeElement !== t)) return
}

function n0(e, t) {
    return e.map((n, r) => e[(t + r) % e.length])
}
const r0 = Xg,
    o0 = Qg;
var s0 = function(e) {
        if (typeof document > "u") return null;
        var t = Array.isArray(e) ? e[0] : e;
        return t.ownerDocument.body
    },
    yt = new WeakMap,
    xn = new WeakMap,
    wn = {},
    Ir = 0,
    Kc = function(e) {
        return e && (e.host || Kc(e.parentNode))
    },
    i0 = function(e, t) {
        return t.map(function(n) {
            if (e.contains(n)) return n;
            var r = Kc(n);
            return r && e.contains(r) ? r : (console.error("aria-hidden", n, "in not contained inside", e, ". Doing nothing"), null)
        }).filter(function(n) {
            return !!n
        })
    },
    a0 = function(e, t, n, r) {
        var o = i0(t, Array.isArray(e) ? e : [e]);
        wn[n] || (wn[n] = new WeakMap);
        var s = wn[n],
            i = [],
            a = new Set,
            c = new Set(o),
            l = function(d) {
                !d || a.has(d) || (a.add(d), l(d.parentNode))
            };
        o.forEach(l);
        var f = function(d) {
            !d || c.has(d) || Array.prototype.forEach.call(d.children, function(h) {
                if (a.has(h)) f(h);
                else {
                    var p = h.getAttribute(r),
                        g = p !== null && p !== "false",
                        m = (yt.get(h) || 0) + 1,
                        b = (s.get(h) || 0) + 1;
                    yt.set(h, m), s.set(h, b), i.push(h), m === 1 && g && xn.set(h, !0), b === 1 && h.setAttribute(n, "true"), g || h.setAttribute(r, "true")
                }
            })
        };
        return f(t), a.clear(), Ir++,
            function() {
                i.forEach(function(d) {
                    var h = yt.get(d) - 1,
                        p = s.get(d) - 1;
                    yt.set(d, h), s.set(d, p), h || (xn.has(d) || d.removeAttribute(r), xn.delete(d)), p || d.removeAttribute(n)
                }), Ir--, Ir || (yt = new WeakMap, yt = new WeakMap, xn = new WeakMap, wn = {})
            }
    },
    c0 = function(e, t, n) {
        n === void 0 && (n = "data-aria-hidden");
        var r = Array.from(Array.isArray(e) ? e : [e]),
            o = t || s0(e);
        return o ? (r.push.apply(r, Array.from(o.querySelectorAll("[aria-live]"))), a0(r, o, n, "aria-hidden")) : function() {
            return null
        }
    },
    Re = function() {
        return Re = Object.assign || function(t) {
            for (var n, r = 1, o = arguments.length; r < o; r++) {
                n = arguments[r];
                for (var s in n) Object.prototype.hasOwnProperty.call(n, s) && (t[s] = n[s])
            }
            return t
        }, Re.apply(this, arguments)
    };

function zc(e, t) {
    var n = {};
    for (var r in e) Object.prototype.hasOwnProperty.call(e, r) && t.indexOf(r) < 0 && (n[r] = e[r]);
    if (e != null && typeof Object.getOwnPropertySymbols == "function")
        for (var o = 0, r = Object.getOwnPropertySymbols(e); o < r.length; o++) t.indexOf(r[o]) < 0 && Object.prototype.propertyIsEnumerable.call(e, r[o]) && (n[r[o]] = e[r[o]]);
    return n
}

function l0(e, t, n) {
    if (n || arguments.length === 2)
        for (var r = 0, o = t.length, s; r < o; r++)(s || !(r in t)) && (s || (s = Array.prototype.slice.call(t, 0, r)), s[r] = t[r]);
    return e.concat(s || Array.prototype.slice.call(t))
}
var Tn = "right-scroll-bar-position",
    Mn = "width-before-scroll-bar",
    u0 = "with-scroll-bars-hidden",
    f0 = "--removed-body-scroll-bar-size";

function d0(e, t) {
    return typeof e == "function" ? e(t) : e && (e.current = t), e
}

function h0(e, t) {
    var n = u.useState(function() {
        return {
            value: e,
            callback: t,
            facade: {
                get current() {
                    return n.value
                },
                set current(r) {
                    var o = n.value;
                    o !== r && (n.value = r, n.callback(r, o))
                }
            }
        }
    })[0];
    return n.callback = t, n.facade
}

function p0(e, t) {
    return h0(t || null, function(n) {
        return e.forEach(function(r) {
            return d0(r, n)
        })
    })
}

function m0(e) {
    return e
}

function g0(e, t) {
    t === void 0 && (t = m0);
    var n = [],
        r = !1,
        o = {
            read: function() {
                if (r) throw new Error("Sidecar: could not `read` from an `assigned` medium. `read` could be used only with `useMedium`.");
                return n.length ? n[n.length - 1] : e
            },
            useMedium: function(s) {
                var i = t(s, r);
                return n.push(i),
                    function() {
                        n = n.filter(function(a) {
                            return a !== i
                        })
                    }
            },
            assignSyncMedium: function(s) {
                for (r = !0; n.length;) {
                    var i = n;
                    n = [], i.forEach(s)
                }
                n = {
                    push: function(a) {
                        return s(a)
                    },
                    filter: function() {
                        return n
                    }
                }
            },
            assignMedium: function(s) {
                r = !0;
                var i = [];
                if (n.length) {
                    var a = n;
                    n = [], a.forEach(s), i = n
                }
                var c = function() {
                        var f = i;
                        i = [], f.forEach(s)
                    },
                    l = function() {
                        return Promise.resolve().then(c)
                    };
                l(), n = {
                    push: function(f) {
                        i.push(f), l()
                    },
                    filter: function(f) {
                        return i = i.filter(f), n
                    }
                }
            }
        };
    return o
}

function v0(e) {
    e === void 0 && (e = {});
    var t = g0(null);
    return t.options = Re({
        async: !0,
        ssr: !1
    }, e), t
}
var Yc = function(e) {
    var t = e.sideCar,
        n = zc(e, ["sideCar"]);
    if (!t) throw new Error("Sidecar: please provide `sideCar` property to import the right car");
    var r = t.read();
    if (!r) throw new Error("Sidecar medium not found");
    return u.createElement(r, Re({}, n))
};
Yc.isSideCarExport = !0;

function y0(e, t) {
    return e.useMedium(t), Yc
}
var Xc = v0(),
    Vr = function() {},
    ur = u.forwardRef(function(e, t) {
        var n = u.useRef(null),
            r = u.useState({
                onScrollCapture: Vr,
                onWheelCapture: Vr,
                onTouchMoveCapture: Vr
            }),
            o = r[0],
            s = r[1],
            i = e.forwardProps,
            a = e.children,
            c = e.className,
            l = e.removeScrollBar,
            f = e.enabled,
            d = e.shards,
            h = e.sideCar,
            p = e.noIsolation,
            g = e.inert,
            m = e.allowPinchZoom,
            b = e.as,
            x = b === void 0 ? "div" : b,
            v = zc(e, ["forwardProps", "children", "className", "removeScrollBar", "enabled", "shards", "sideCar", "noIsolation", "inert", "allowPinchZoom", "as"]),
            y = h,
            C = p0([n, t]),
            $ = Re(Re({}, v), o);
        return u.createElement(u.Fragment, null, f && u.createElement(y, {
            sideCar: Xc,
            removeScrollBar: l,
            shards: d,
            noIsolation: p,
            inert: g,
            setCallbacks: s,
            allowPinchZoom: !!m,
            lockRef: n
        }), i ? u.cloneElement(u.Children.only(a), Re(Re({}, $), {
            ref: C
        })) : u.createElement(x, Re({}, $, {
            className: c,
            ref: C
        }), a))
    });
ur.defaultProps = {
    enabled: !0,
    removeScrollBar: !0,
    inert: !1
};
ur.classNames = {
    fullWidth: Mn,
    zeroRight: Tn
};
var Pi, b0 = function() {
    if (Pi) return Pi;
    if (typeof __webpack_nonce__ < "u") return __webpack_nonce__
};

function x0() {
    if (!document) return null;
    var e = document.createElement("style");
    e.type = "text/css";
    var t = b0();
    return t && e.setAttribute("nonce", t), e
}

function w0(e, t) {
    e.styleSheet ? e.styleSheet.cssText = t : e.appendChild(document.createTextNode(t))
}

function C0(e) {
    var t = document.head || document.getElementsByTagName("head")[0];
    t.appendChild(e)
}
var A0 = function() {
        var e = 0,
            t = null;
        return {
            add: function(n) {
                e == 0 && (t = x0()) && (w0(t, n), C0(t)), e++
            },
            remove: function() {
                e--, !e && t && (t.parentNode && t.parentNode.removeChild(t), t = null)
            }
        }
    },
    $0 = function() {
        var e = A0();
        return function(t, n) {
            u.useEffect(function() {
                return e.add(t),
                    function() {
                        e.remove()
                    }
            }, [t && n])
        }
    },
    Zc = function() {
        var e = $0(),
            t = function(n) {
                var r = n.styles,
                    o = n.dynamic;
                return e(r, o), null
            };
        return t
    },
    P0 = {
        left: 0,
        top: 0,
        right: 0,
        gap: 0
    },
    Nr = function(e) {
        return parseInt(e || "", 10) || 0
    },
    S0 = function(e) {
        var t = window.getComputedStyle(document.body),
            n = t[e === "padding" ? "paddingLeft" : "marginLeft"],
            r = t[e === "padding" ? "paddingTop" : "marginTop"],
            o = t[e === "padding" ? "paddingRight" : "marginRight"];
        return [Nr(n), Nr(r), Nr(o)]
    },
    E0 = function(e) {
        if (e === void 0 && (e = "margin"), typeof window > "u") return P0;
        var t = S0(e),
            n = document.documentElement.clientWidth,
            r = window.innerWidth;
        return {
            left: t[0],
            top: t[1],
            right: t[2],
            gap: Math.max(0, r - n + t[2] - t[0])
        }
    },
    T0 = Zc(),
    M0 = function(e, t, n, r) {
        var o = e.left,
            s = e.top,
            i = e.right,
            a = e.gap;
        return n === void 0 && (n = "margin"), `
  .`.concat(u0, ` {
   overflow: hidden `).concat(r, `;
   padding-right: `).concat(a, "px ").concat(r, `;
  }
  body {
    overflow: hidden `).concat(r, `;
    overscroll-behavior: contain;
    `).concat([t && "position: relative ".concat(r, ";"), n === "margin" && `
    padding-left: `.concat(o, `px;
    padding-top: `).concat(s, `px;
    padding-right: `).concat(i, `px;
    margin-left:0;
    margin-top:0;
    margin-right: `).concat(a, "px ").concat(r, `;
    `), n === "padding" && "padding-right: ".concat(a, "px ").concat(r, ";")].filter(Boolean).join(""), `
  }
  
  .`).concat(Tn, ` {
    right: `).concat(a, "px ").concat(r, `;
  }
  
  .`).concat(Mn, ` {
    margin-right: `).concat(a, "px ").concat(r, `;
  }
  
  .`).concat(Tn, " .").concat(Tn, ` {
    right: 0 `).concat(r, `;
  }
  
  .`).concat(Mn, " .").concat(Mn, ` {
    margin-right: 0 `).concat(r, `;
  }
  
  body {
    `).concat(f0, ": ").concat(a, `px;
  }
`)
    },
    D0 = function(e) {
        var t = e.noRelative,
            n = e.noImportant,
            r = e.gapMode,
            o = r === void 0 ? "margin" : r,
            s = u.useMemo(function() {
                return E0(o)
            }, [o]);
        return u.createElement(T0, {
            styles: M0(s, !t, o, n ? "" : "!important")
        })
    },
    io = !1;
if (typeof window < "u") try {
    var Cn = Object.defineProperty({}, "passive", {
        get: function() {
            return io = !0, !0
        }
    });
    window.addEventListener("test", Cn, Cn), window.removeEventListener("test", Cn, Cn)
} catch {
    io = !1
}
var bt = io ? {
        passive: !1
    } : !1,
    R0 = function(e) {
        return e.tagName === "TEXTAREA"
    },
    qc = function(e, t) {
        var n = window.getComputedStyle(e);
        return n[t] !== "hidden" && !(n.overflowY === n.overflowX && !R0(e) && n[t] === "visible")
    },
    O0 = function(e) {
        return qc(e, "overflowY")
    },
    _0 = function(e) {
        return qc(e, "overflowX")
    },
    Si = function(e, t) {
        var n = t;
        do {
            typeof ShadowRoot < "u" && n instanceof ShadowRoot && (n = n.host);
            var r = Qc(e, n);
            if (r) {
                var o = Jc(e, n),
                    s = o[1],
                    i = o[2];
                if (s > i) return !0
            }
            n = n.parentNode
        } while (n && n !== document.body);
        return !1
    },
    k0 = function(e) {
        var t = e.scrollTop,
            n = e.scrollHeight,
            r = e.clientHeight;
        return [t, n, r]
    },
    L0 = function(e) {
        var t = e.scrollLeft,
            n = e.scrollWidth,
            r = e.clientWidth;
        return [t, n, r]
    },
    Qc = function(e, t) {
        return e === "v" ? O0(t) : _0(t)
    },
    Jc = function(e, t) {
        return e === "v" ? k0(t) : L0(t)
    },
    I0 = function(e, t) {
        return e === "h" && t === "rtl" ? -1 : 1
    },
    V0 = function(e, t, n, r, o) {
        var s = I0(e, window.getComputedStyle(t).direction),
            i = s * r,
            a = n.target,
            c = t.contains(a),
            l = !1,
            f = i > 0,
            d = 0,
            h = 0;
        do {
            var p = Jc(e, a),
                g = p[0],
                m = p[1],
                b = p[2],
                x = m - b - s * g;
            (g || x) && Qc(e, a) && (d += x, h += g), a = a.parentNode
        } while (!c && a !== document.body || c && (t.contains(a) || t === a));
        return (f && (o && d === 0 || !o && i > d) || !f && (o && h === 0 || !o && -i > h)) && (l = !0), l
    },
    An = function(e) {
        return "changedTouches" in e ? [e.changedTouches[0].clientX, e.changedTouches[0].clientY] : [0, 0]
    },
    Ei = function(e) {
        return [e.deltaX, e.deltaY]
    },
    Ti = function(e) {
        return e && "current" in e ? e.current : e
    },
    N0 = function(e, t) {
        return e[0] === t[0] && e[1] === t[1]
    },
    F0 = function(e) {
        return `
  .block-interactivity-`.concat(e, ` {pointer-events: none;}
  .allow-interactivity-`).concat(e, ` {pointer-events: all;}
`)
    },
    j0 = 0,
    xt = [];

function B0(e) {
    var t = u.useRef([]),
        n = u.useRef([0, 0]),
        r = u.useRef(),
        o = u.useState(j0++)[0],
        s = u.useState(function() {
            return Zc()
        })[0],
        i = u.useRef(e);
    u.useEffect(function() {
        i.current = e
    }, [e]), u.useEffect(function() {
        if (e.inert) {
            document.body.classList.add("block-interactivity-".concat(o));
            var m = l0([e.lockRef.current], (e.shards || []).map(Ti), !0).filter(Boolean);
            return m.forEach(function(b) {
                    return b.classList.add("allow-interactivity-".concat(o))
                }),
                function() {
                    document.body.classList.remove("block-interactivity-".concat(o)), m.forEach(function(b) {
                        return b.classList.remove("allow-interactivity-".concat(o))
                    })
                }
        }
    }, [e.inert, e.lockRef.current, e.shards]);
    var a = u.useCallback(function(m, b) {
            if ("touches" in m && m.touches.length === 2) return !i.current.allowPinchZoom;
            var x = An(m),
                v = n.current,
                y = "deltaX" in m ? m.deltaX : v[0] - x[0],
                C = "deltaY" in m ? m.deltaY : v[1] - x[1],
                $, A = m.target,
                w = Math.abs(y) > Math.abs(C) ? "h" : "v";
            if ("touches" in m && w === "h" && A.type === "range") return !1;
            var S = Si(w, A);
            if (!S) return !0;
            if (S ? $ = w : ($ = w === "v" ? "h" : "v", S = Si(w, A)), !S) return !1;
            if (!r.current && "changedTouches" in m && (y || C) && (r.current = $), !$) return !0;
            var E = r.current || $;
            return V0(E, b, m, E === "h" ? y : C, !0)
        }, []),
        c = u.useCallback(function(m) {
            var b = m;
            if (!(!xt.length || xt[xt.length - 1] !== s)) {
                var x = "deltaY" in b ? Ei(b) : An(b),
                    v = t.current.filter(function($) {
                        return $.name === b.type && $.target === b.target && N0($.delta, x)
                    })[0];
                if (v && v.should) {
                    b.cancelable && b.preventDefault();
                    return
                }
                if (!v) {
                    var y = (i.current.shards || []).map(Ti).filter(Boolean).filter(function($) {
                            return $.contains(b.target)
                        }),
                        C = y.length > 0 ? a(b, y[0]) : !i.current.noIsolation;
                    C && b.cancelable && b.preventDefault()
                }
            }
        }, []),
        l = u.useCallback(function(m, b, x, v) {
            var y = {
                name: m,
                delta: b,
                target: x,
                should: v
            };
            t.current.push(y), setTimeout(function() {
                t.current = t.current.filter(function(C) {
                    return C !== y
                })
            }, 1)
        }, []),
        f = u.useCallback(function(m) {
            n.current = An(m), r.current = void 0
        }, []),
        d = u.useCallback(function(m) {
            l(m.type, Ei(m), m.target, a(m, e.lockRef.current))
        }, []),
        h = u.useCallback(function(m) {
            l(m.type, An(m), m.target, a(m, e.lockRef.current))
        }, []);
    u.useEffect(function() {
        return xt.push(s), e.setCallbacks({
                onScrollCapture: d,
                onWheelCapture: d,
                onTouchMoveCapture: h
            }), document.addEventListener("wheel", c, bt), document.addEventListener("touchmove", c, bt), document.addEventListener("touchstart", f, bt),
            function() {
                xt = xt.filter(function(m) {
                    return m !== s
                }), document.removeEventListener("wheel", c, bt), document.removeEventListener("touchmove", c, bt), document.removeEventListener("touchstart", f, bt)
            }
    }, []);
    var p = e.removeScrollBar,
        g = e.inert;
    return u.createElement(u.Fragment, null, g ? u.createElement(s, {
        styles: F0(o)
    }) : null, p ? u.createElement(D0, {
        gapMode: "margin"
    }) : null)
}
const U0 = y0(Xc, B0);
var el = u.forwardRef(function(e, t) {
    return u.createElement(ur, Re({}, e, {
        ref: t,
        sideCar: U0
    }))
});
el.classNames = ur.classNames;
const W0 = el,
    ao = ["Enter", " "],
    H0 = ["ArrowDown", "PageUp", "Home"],
    tl = ["ArrowUp", "PageDown", "End"],
    G0 = [...H0, ...tl],
    K0 = {
        ltr: [...ao, "ArrowRight"],
        rtl: [...ao, "ArrowLeft"]
    },
    z0 = {
        ltr: ["ArrowLeft"],
        rtl: ["ArrowRight"]
    },
    fr = "Menu",
    [tn, Y0, X0] = Cc(fr),
    [mt, nl] = Lt(fr, [X0, lr, Hc]),
    dr = lr(),
    rl = Hc(),
    [ol, st] = mt(fr),
    [Z0, fn] = mt(fr),
    q0 = e => {
        const {
            __scopeMenu: t,
            open: n = !1,
            children: r,
            dir: o,
            onOpenChange: s,
            modal: i = !0
        } = e, a = dr(t), [c, l] = u.useState(null), f = u.useRef(!1), d = Ce(s), h = Ac(o);
        return u.useEffect(() => {
            const p = () => {
                    f.current = !0, document.addEventListener("pointerdown", g, {
                        capture: !0,
                        once: !0
                    }), document.addEventListener("pointermove", g, {
                        capture: !0,
                        once: !0
                    })
                },
                g = () => f.current = !1;
            return document.addEventListener("keydown", p, {
                capture: !0
            }), () => {
                document.removeEventListener("keydown", p, {
                    capture: !0
                }), document.removeEventListener("pointerdown", g, {
                    capture: !0
                }), document.removeEventListener("pointermove", g, {
                    capture: !0
                })
            }
        }, []), u.createElement(Uo, a, u.createElement(ol, {
            scope: t,
            open: n,
            onOpenChange: d,
            content: c,
            onContentChange: l
        }, u.createElement(Z0, {
            scope: t,
            onClose: u.useCallback(() => d(!1), [d]),
            isUsingKeyboardRef: f,
            dir: h,
            modal: i
        }, r)))
    },
    sl = u.forwardRef((e, t) => {
        const {
            __scopeMenu: n,
            ...r
        } = e, o = dr(n);
        return u.createElement(Bc, V({}, o, r, {
            ref: t
        }))
    }),
    il = "MenuPortal",
    [Q0, al] = mt(il, {
        forceMount: void 0
    }),
    J0 = e => {
        const {
            __scopeMenu: t,
            forceMount: n,
            children: r,
            container: o
        } = e, s = st(il, t);
        return u.createElement(Q0, {
            scope: t,
            forceMount: n
        }, u.createElement(Nt, {
            present: n || s.open
        }, u.createElement(Bg, {
            asChild: !0,
            container: o
        }, r)))
    },
    Te = "MenuContent",
    [e1, Ho] = mt(Te),
    t1 = u.forwardRef((e, t) => {
        const n = al(Te, e.__scopeMenu),
            {
                forceMount: r = n.forceMount,
                ...o
            } = e,
            s = st(Te, e.__scopeMenu),
            i = fn(Te, e.__scopeMenu);
        return u.createElement(tn.Provider, {
            scope: e.__scopeMenu
        }, u.createElement(Nt, {
            present: r || s.open
        }, u.createElement(tn.Slot, {
            scope: e.__scopeMenu
        }, i.modal ? u.createElement(n1, V({}, o, {
            ref: t
        })) : u.createElement(r1, V({}, o, {
            ref: t
        })))))
    }),
    n1 = u.forwardRef((e, t) => {
        const n = st(Te, e.__scopeMenu),
            r = u.useRef(null),
            o = le(t, r);
        return u.useEffect(() => {
            const s = r.current;
            if (s) return c0(s)
        }, []), u.createElement(Go, V({}, e, {
            ref: o,
            trapFocus: n.open,
            disableOutsidePointerEvents: n.open,
            disableOutsideScroll: !0,
            onFocusOutside: K(e.onFocusOutside, s => s.preventDefault(), {
                checkForDefaultPrevented: !1
            }),
            onDismiss: () => n.onOpenChange(!1)
        }))
    }),
    r1 = u.forwardRef((e, t) => {
        const n = st(Te, e.__scopeMenu);
        return u.createElement(Go, V({}, e, {
            ref: t,
            trapFocus: !1,
            disableOutsidePointerEvents: !1,
            disableOutsideScroll: !1,
            onDismiss: () => n.onOpenChange(!1)
        }))
    }),
    Go = u.forwardRef((e, t) => {
        const {
            __scopeMenu: n,
            loop: r = !1,
            trapFocus: o,
            onOpenAutoFocus: s,
            onCloseAutoFocus: i,
            disableOutsidePointerEvents: a,
            onEntryFocus: c,
            onEscapeKeyDown: l,
            onPointerDownOutside: f,
            onFocusOutside: d,
            onInteractOutside: h,
            onDismiss: p,
            disableOutsideScroll: g,
            ...m
        } = e, b = st(Te, n), x = fn(Te, n), v = dr(n), y = rl(n), C = Y0(n), [$, A] = u.useState(null), w = u.useRef(null), S = le(t, w, b.onContentChange), E = u.useRef(0), j = u.useRef(""), I = u.useRef(0), G = u.useRef(null), _ = u.useRef("right"), L = u.useRef(0), k = g ? W0 : u.Fragment, U = g ? {
            as: Rt,
            allowPinchZoom: !0
        } : void 0, Z = N => {
            var Y, W;
            const P = j.current + N,
                T = C().filter(z => !z.disabled),
                R = document.activeElement,
                D = (Y = T.find(z => z.ref.current === R)) === null || Y === void 0 ? void 0 : Y.textValue,
                O = T.map(z => z.textValue),
                F = A1(O, P, D),
                H = (W = T.find(z => z.textValue === F)) === null || W === void 0 ? void 0 : W.ref.current;
            (function z(X) {
                j.current = X, window.clearTimeout(E.current), X !== "" && (E.current = window.setTimeout(() => z(""), 1e3))
            })(P), H && setTimeout(() => H.focus())
        };
        u.useEffect(() => () => window.clearTimeout(E.current), []), Fm();
        const q = u.useCallback(N => {
            var Y, W;
            return _.current === ((Y = G.current) === null || Y === void 0 ? void 0 : Y.side) && P1(N, (W = G.current) === null || W === void 0 ? void 0 : W.area)
        }, []);
        return u.createElement(e1, {
            scope: n,
            searchRef: j,
            onItemEnter: u.useCallback(N => {
                q(N) && N.preventDefault()
            }, [q]),
            onItemLeave: u.useCallback(N => {
                var Y;
                q(N) || ((Y = w.current) === null || Y === void 0 || Y.focus(), A(null))
            }, [q]),
            onTriggerLeave: u.useCallback(N => {
                q(N) && N.preventDefault()
            }, [q]),
            pointerGraceTimerRef: I,
            onPointerGraceIntentChange: u.useCallback(N => {
                G.current = N
            }, [])
        }, u.createElement(k, U, u.createElement(jm, {
            asChild: !0,
            trapped: o,
            onMountAutoFocus: K(s, N => {
                var Y;
                N.preventDefault(), (Y = w.current) === null || Y === void 0 || Y.focus()
            }),
            onUnmountAutoFocus: i
        }, u.createElement($c, {
            asChild: !0,
            disableOutsidePointerEvents: a,
            onEscapeKeyDown: l,
            onPointerDownOutside: f,
            onFocusOutside: d,
            onInteractOutside: h,
            onDismiss: p
        }, u.createElement(r0, V({
            asChild: !0
        }, y, {
            dir: x.dir,
            orientation: "vertical",
            loop: r,
            currentTabStopId: $,
            onCurrentTabStopIdChange: A,
            onEntryFocus: K(c, N => {
                x.isUsingKeyboardRef.current || N.preventDefault()
            })
        }), u.createElement(Uc, V({
            role: "menu",
            "aria-orientation": "vertical",
            "data-state": hl(b.open),
            "data-radix-menu-content": "",
            dir: x.dir
        }, v, m, {
            ref: S,
            style: {
                outline: "none",
                ...m.style
            },
            onKeyDown: K(m.onKeyDown, N => {
                const W = N.target.closest("[data-radix-menu-content]") === N.currentTarget,
                    P = N.ctrlKey || N.altKey || N.metaKey,
                    T = N.key.length === 1;
                W && (N.key === "Tab" && N.preventDefault(), !P && T && Z(N.key));
                const R = w.current;
                if (N.target !== R || !G0.includes(N.key)) return;
                N.preventDefault();
                const O = C().filter(F => !F.disabled).map(F => F.ref.current);
                tl.includes(N.key) && O.reverse(), w1(O)
            }),
            onBlur: K(e.onBlur, N => {
                N.currentTarget.contains(N.target) || (window.clearTimeout(E.current), j.current = "")
            }),
            onPointerMove: K(e.onPointerMove, nn(N => {
                const Y = N.target,
                    W = L.current !== N.clientX;
                if (N.currentTarget.contains(Y) && W) {
                    const P = N.clientX > L.current ? "right" : "left";
                    _.current = P, L.current = N.clientX
                }
            }))
        })))))))
    }),
    o1 = u.forwardRef((e, t) => {
        const {
            __scopeMenu: n,
            ...r
        } = e;
        return u.createElement(ue.div, V({
            role: "group"
        }, r, {
            ref: t
        }))
    }),
    s1 = u.forwardRef((e, t) => {
        const {
            __scopeMenu: n,
            ...r
        } = e;
        return u.createElement(ue.div, V({}, r, {
            ref: t
        }))
    }),
    co = "MenuItem",
    Mi = "menu.itemSelect",
    Ko = u.forwardRef((e, t) => {
        const {
            disabled: n = !1,
            onSelect: r,
            ...o
        } = e, s = u.useRef(null), i = fn(co, e.__scopeMenu), a = Ho(co, e.__scopeMenu), c = le(t, s), l = u.useRef(!1), f = () => {
            const d = s.current;
            if (!n && d) {
                const h = new CustomEvent(Mi, {
                    bubbles: !0,
                    cancelable: !0
                });
                d.addEventListener(Mi, p => r?.(p), {
                    once: !0
                }), wc(d, h), h.defaultPrevented ? l.current = !1 : i.onClose()
            }
        };
        return u.createElement(cl, V({}, o, {
            ref: c,
            disabled: n,
            onClick: K(e.onClick, f),
            onPointerDown: d => {
                var h;
                (h = e.onPointerDown) === null || h === void 0 || h.call(e, d), l.current = !0
            },
            onPointerUp: K(e.onPointerUp, d => {
                var h;
                l.current || (h = d.currentTarget) === null || h === void 0 || h.click()
            }),
            onKeyDown: K(e.onKeyDown, d => {
                const h = a.searchRef.current !== "";
                n || h && d.key === " " || ao.includes(d.key) && (d.currentTarget.click(), d.preventDefault())
            })
        }))
    }),
    cl = u.forwardRef((e, t) => {
        const {
            __scopeMenu: n,
            disabled: r = !1,
            textValue: o,
            ...s
        } = e, i = Ho(co, n), a = rl(n), c = u.useRef(null), l = le(t, c), [f, d] = u.useState(!1), [h, p] = u.useState("");
        return u.useEffect(() => {
            const g = c.current;
            if (g) {
                var m;
                p(((m = g.textContent) !== null && m !== void 0 ? m : "").trim())
            }
        }, [s.children]), u.createElement(tn.ItemSlot, {
            scope: n,
            disabled: r,
            textValue: o ?? h
        }, u.createElement(o0, V({
            asChild: !0
        }, a, {
            focusable: !r
        }), u.createElement(ue.div, V({
            role: "menuitem",
            "data-highlighted": f ? "" : void 0,
            "aria-disabled": r || void 0,
            "data-disabled": r ? "" : void 0
        }, s, {
            ref: l,
            onPointerMove: K(e.onPointerMove, nn(g => {
                r ? i.onItemLeave(g) : (i.onItemEnter(g), g.defaultPrevented || g.currentTarget.focus())
            })),
            onPointerLeave: K(e.onPointerLeave, nn(g => i.onItemLeave(g))),
            onFocus: K(e.onFocus, () => d(!0)),
            onBlur: K(e.onBlur, () => d(!1))
        }))))
    }),
    i1 = u.forwardRef((e, t) => {
        const {
            checked: n = !1,
            onCheckedChange: r,
            ...o
        } = e;
        return u.createElement(ul, {
            scope: e.__scopeMenu,
            checked: n
        }, u.createElement(Ko, V({
            role: "menuitemcheckbox",
            "aria-checked": Kn(n) ? "mixed" : n
        }, o, {
            ref: t,
            "data-state": zo(n),
            onSelect: K(o.onSelect, () => r?.(Kn(n) ? !0 : !n), {
                checkForDefaultPrevented: !1
            })
        })))
    }),
    a1 = "MenuRadioGroup",
    [c1, l1] = mt(a1, {
        value: void 0,
        onValueChange: () => {}
    }),
    u1 = u.forwardRef((e, t) => {
        const {
            value: n,
            onValueChange: r,
            ...o
        } = e, s = Ce(r);
        return u.createElement(c1, {
            scope: e.__scopeMenu,
            value: n,
            onValueChange: s
        }, u.createElement(o1, V({}, o, {
            ref: t
        })))
    }),
    f1 = "MenuRadioItem",
    d1 = u.forwardRef((e, t) => {
        const {
            value: n,
            ...r
        } = e, o = l1(f1, e.__scopeMenu), s = n === o.value;
        return u.createElement(ul, {
            scope: e.__scopeMenu,
            checked: s
        }, u.createElement(Ko, V({
            role: "menuitemradio",
            "aria-checked": s
        }, r, {
            ref: t,
            "data-state": zo(s),
            onSelect: K(r.onSelect, () => {
                var i;
                return (i = o.onValueChange) === null || i === void 0 ? void 0 : i.call(o, n)
            }, {
                checkForDefaultPrevented: !1
            })
        })))
    }),
    ll = "MenuItemIndicator",
    [ul, h1] = mt(ll, {
        checked: !1
    }),
    p1 = u.forwardRef((e, t) => {
        const {
            __scopeMenu: n,
            forceMount: r,
            ...o
        } = e, s = h1(ll, n);
        return u.createElement(Nt, {
            present: r || Kn(s.checked) || s.checked === !0
        }, u.createElement(ue.span, V({}, o, {
            ref: t,
            "data-state": zo(s.checked)
        })))
    }),
    m1 = u.forwardRef((e, t) => {
        const {
            __scopeMenu: n,
            ...r
        } = e;
        return u.createElement(ue.div, V({
            role: "separator",
            "aria-orientation": "horizontal"
        }, r, {
            ref: t
        }))
    }),
    fl = "MenuSub",
    [g1, dl] = mt(fl),
    v1 = e => {
        const {
            __scopeMenu: t,
            children: n,
            open: r = !1,
            onOpenChange: o
        } = e, s = st(fl, t), i = dr(t), [a, c] = u.useState(null), [l, f] = u.useState(null), d = Ce(o);
        return u.useEffect(() => (s.open === !1 && d(!1), () => d(!1)), [s.open, d]), u.createElement(Uo, i, u.createElement(ol, {
            scope: t,
            open: r,
            onOpenChange: d,
            content: l,
            onContentChange: f
        }, u.createElement(g1, {
            scope: t,
            contentId: _t(),
            triggerId: _t(),
            trigger: a,
            onTriggerChange: c
        }, n)))
    },
    $n = "MenuSubTrigger",
    y1 = u.forwardRef((e, t) => {
        const n = st($n, e.__scopeMenu),
            r = fn($n, e.__scopeMenu),
            o = dl($n, e.__scopeMenu),
            s = Ho($n, e.__scopeMenu),
            i = u.useRef(null),
            {
                pointerGraceTimerRef: a,
                onPointerGraceIntentChange: c
            } = s,
            l = {
                __scopeMenu: e.__scopeMenu
            },
            f = u.useCallback(() => {
                i.current && window.clearTimeout(i.current), i.current = null
            }, []);
        return u.useEffect(() => f, [f]), u.useEffect(() => {
            const d = a.current;
            return () => {
                window.clearTimeout(d), c(null)
            }
        }, [a, c]), u.createElement(sl, V({
            asChild: !0
        }, l), u.createElement(cl, V({
            id: o.triggerId,
            "aria-haspopup": "menu",
            "aria-expanded": n.open,
            "aria-controls": o.contentId,
            "data-state": hl(n.open)
        }, e, {
            ref: sr(t, o.onTriggerChange),
            onClick: d => {
                var h;
                (h = e.onClick) === null || h === void 0 || h.call(e, d), !(e.disabled || d.defaultPrevented) && (d.currentTarget.focus(), n.open || n.onOpenChange(!0))
            },
            onPointerMove: K(e.onPointerMove, nn(d => {
                s.onItemEnter(d), !d.defaultPrevented && !e.disabled && !n.open && !i.current && (s.onPointerGraceIntentChange(null), i.current = window.setTimeout(() => {
                    n.onOpenChange(!0), f()
                }, 100))
            })),
            onPointerLeave: K(e.onPointerLeave, nn(d => {
                var h;
                f();
                const p = (h = n.content) === null || h === void 0 ? void 0 : h.getBoundingClientRect();
                if (p) {
                    var g;
                    const m = (g = n.content) === null || g === void 0 ? void 0 : g.dataset.side,
                        b = m === "right",
                        x = b ? -5 : 5,
                        v = p[b ? "left" : "right"],
                        y = p[b ? "right" : "left"];
                    s.onPointerGraceIntentChange({
                        area: [{
                            x: d.clientX + x,
                            y: d.clientY
                        }, {
                            x: v,
                            y: p.top
                        }, {
                            x: y,
                            y: p.top
                        }, {
                            x: y,
                            y: p.bottom
                        }, {
                            x: v,
                            y: p.bottom
                        }],
                        side: m
                    }), window.clearTimeout(a.current), a.current = window.setTimeout(() => s.onPointerGraceIntentChange(null), 300)
                } else {
                    if (s.onTriggerLeave(d), d.defaultPrevented) return;
                    s.onPointerGraceIntentChange(null)
                }
            })),
            onKeyDown: K(e.onKeyDown, d => {
                const h = s.searchRef.current !== "";
                if (!(e.disabled || h && d.key === " ") && K0[r.dir].includes(d.key)) {
                    var p;
                    n.onOpenChange(!0), (p = n.content) === null || p === void 0 || p.focus(), d.preventDefault()
                }
            })
        })))
    }),
    b1 = "MenuSubContent",
    x1 = u.forwardRef((e, t) => {
        const n = al(Te, e.__scopeMenu),
            {
                forceMount: r = n.forceMount,
                ...o
            } = e,
            s = st(Te, e.__scopeMenu),
            i = fn(Te, e.__scopeMenu),
            a = dl(b1, e.__scopeMenu),
            c = u.useRef(null),
            l = le(t, c);
        return u.createElement(tn.Provider, {
            scope: e.__scopeMenu
        }, u.createElement(Nt, {
            present: r || s.open
        }, u.createElement(tn.Slot, {
            scope: e.__scopeMenu
        }, u.createElement(Go, V({
            id: a.contentId,
            "aria-labelledby": a.triggerId
        }, o, {
            ref: l,
            align: "start",
            side: i.dir === "rtl" ? "left" : "right",
            disableOutsidePointerEvents: !1,
            disableOutsideScroll: !1,
            trapFocus: !1,
            onOpenAutoFocus: f => {
                var d;
                i.isUsingKeyboardRef.current && ((d = c.current) === null || d === void 0 || d.focus()), f.preventDefault()
            },
            onCloseAutoFocus: f => f.preventDefault(),
            onFocusOutside: K(e.onFocusOutside, f => {
                f.target !== a.trigger && s.onOpenChange(!1)
            }),
            onEscapeKeyDown: K(e.onEscapeKeyDown, f => {
                i.onClose(), f.preventDefault()
            }),
            onKeyDown: K(e.onKeyDown, f => {
                const d = f.currentTarget.contains(f.target),
                    h = z0[i.dir].includes(f.key);
                if (d && h) {
                    var p;
                    s.onOpenChange(!1), (p = a.trigger) === null || p === void 0 || p.focus(), f.preventDefault()
                }
            })
        })))))
    });

function hl(e) {
    return e ? "open" : "closed"
}

function Kn(e) {
    return e === "indeterminate"
}

function zo(e) {
    return Kn(e) ? "indeterminate" : e ? "checked" : "unchecked"
}

function w1(e) {
    const t = document.activeElement;
    for (const n of e)
        if (n === t || (n.focus(), document.activeElement !== t)) return
}

function C1(e, t) {
    return e.map((n, r) => e[(t + r) % e.length])
}

function A1(e, t, n) {
    const o = t.length > 1 && Array.from(t).every(l => l === t[0]) ? t[0] : t,
        s = n ? e.indexOf(n) : -1;
    let i = C1(e, Math.max(s, 0));
    o.length === 1 && (i = i.filter(l => l !== n));
    const c = i.find(l => l.toLowerCase().startsWith(o.toLowerCase()));
    return c !== n ? c : void 0
}

function $1(e, t) {
    const {
        x: n,
        y: r
    } = e;
    let o = !1;
    for (let s = 0, i = t.length - 1; s < t.length; i = s++) {
        const a = t[s].x,
            c = t[s].y,
            l = t[i].x,
            f = t[i].y;
        c > r != f > r && n < (l - a) * (r - c) / (f - c) + a && (o = !o)
    }
    return o
}

function P1(e, t) {
    if (!t) return !1;
    const n = {
        x: e.clientX,
        y: e.clientY
    };
    return $1(n, t)
}

function nn(e) {
    return t => t.pointerType === "mouse" ? e(t) : void 0
}
const S1 = q0,
    E1 = sl,
    T1 = J0,
    M1 = t1,
    D1 = s1,
    R1 = Ko,
    O1 = i1,
    _1 = u1,
    k1 = d1,
    L1 = p1,
    I1 = m1,
    V1 = v1,
    N1 = y1,
    F1 = x1,
    pl = "DropdownMenu",
    [j1, ty] = Lt(pl, [nl]),
    de = nl(),
    [B1, ml] = j1(pl),
    U1 = e => {
        const {
            __scopeDropdownMenu: t,
            children: n,
            dir: r,
            open: o,
            defaultOpen: s,
            onOpenChange: i,
            modal: a = !0
        } = e, c = de(t), l = u.useRef(null), [f = !1, d] = ir({
            prop: o,
            defaultProp: s,
            onChange: i
        });
        return u.createElement(B1, {
            scope: t,
            triggerId: _t(),
            triggerRef: l,
            contentId: _t(),
            open: f,
            onOpenChange: d,
            onOpenToggle: u.useCallback(() => d(h => !h), [d]),
            modal: a
        }, u.createElement(S1, V({}, c, {
            open: f,
            onOpenChange: d,
            dir: r,
            modal: a
        }), n))
    },
    W1 = "DropdownMenuTrigger",
    H1 = u.forwardRef((e, t) => {
        const {
            __scopeDropdownMenu: n,
            disabled: r = !1,
            ...o
        } = e, s = ml(W1, n), i = de(n);
        return u.createElement(E1, V({
            asChild: !0
        }, i), u.createElement(ue.button, V({
            type: "button",
            id: s.triggerId,
            "aria-haspopup": "menu",
            "aria-expanded": s.open,
            "aria-controls": s.open ? s.contentId : void 0,
            "data-state": s.open ? "open" : "closed",
            "data-disabled": r ? "" : void 0,
            disabled: r
        }, o, {
            ref: sr(t, s.triggerRef),
            onPointerDown: K(e.onPointerDown, a => {
                !r && a.button === 0 && a.ctrlKey === !1 && (s.onOpenToggle(), s.open || a.preventDefault())
            }),
            onKeyDown: K(e.onKeyDown, a => {
                r || (["Enter", " "].includes(a.key) && s.onOpenToggle(), a.key === "ArrowDown" && s.onOpenChange(!0), ["Enter", " ", "ArrowDown"].includes(a.key) && a.preventDefault())
            })
        })))
    }),
    G1 = e => {
        const {
            __scopeDropdownMenu: t,
            ...n
        } = e, r = de(t);
        return u.createElement(T1, V({}, r, n))
    },
    K1 = "DropdownMenuContent",
    z1 = u.forwardRef((e, t) => {
        const {
            __scopeDropdownMenu: n,
            ...r
        } = e, o = ml(K1, n), s = de(n), i = u.useRef(!1);
        return u.createElement(M1, V({
            id: o.contentId,
            "aria-labelledby": o.triggerId
        }, s, r, {
            ref: t,
            onCloseAutoFocus: K(e.onCloseAutoFocus, a => {
                var c;
                i.current || (c = o.triggerRef.current) === null || c === void 0 || c.focus(), i.current = !1, a.preventDefault()
            }),
            onInteractOutside: K(e.onInteractOutside, a => {
                const c = a.detail.originalEvent,
                    l = c.button === 0 && c.ctrlKey === !0,
                    f = c.button === 2 || l;
                (!o.modal || f) && (i.current = !0)
            }),
            style: {
                ...e.style,
                "--radix-dropdown-menu-content-transform-origin": "var(--radix-popper-transform-origin)",
                "--radix-dropdown-menu-content-available-width": "var(--radix-popper-available-width)",
                "--radix-dropdown-menu-content-available-height": "var(--radix-popper-available-height)",
                "--radix-dropdown-menu-trigger-width": "var(--radix-popper-anchor-width)",
                "--radix-dropdown-menu-trigger-height": "var(--radix-popper-anchor-height)"
            }
        }))
    }),
    Y1 = u.forwardRef((e, t) => {
        const {
            __scopeDropdownMenu: n,
            ...r
        } = e, o = de(n);
        return u.createElement(D1, V({}, o, r, {
            ref: t
        }))
    }),
    X1 = u.forwardRef((e, t) => {
        const {
            __scopeDropdownMenu: n,
            ...r
        } = e, o = de(n);
        return u.createElement(R1, V({}, o, r, {
            ref: t
        }))
    }),
    Z1 = u.forwardRef((e, t) => {
        const {
            __scopeDropdownMenu: n,
            ...r
        } = e, o = de(n);
        return u.createElement(O1, V({}, o, r, {
            ref: t
        }))
    }),
    q1 = u.forwardRef((e, t) => {
        const {
            __scopeDropdownMenu: n,
            ...r
        } = e, o = de(n);
        return u.createElement(_1, V({}, o, r, {
            ref: t
        }))
    }),
    Q1 = u.forwardRef((e, t) => {
        const {
            __scopeDropdownMenu: n,
            ...r
        } = e, o = de(n);
        return u.createElement(k1, V({}, o, r, {
            ref: t
        }))
    }),
    J1 = u.forwardRef((e, t) => {
        const {
            __scopeDropdownMenu: n,
            ...r
        } = e, o = de(n);
        return u.createElement(L1, V({}, o, r, {
            ref: t
        }))
    }),
    ev = u.forwardRef((e, t) => {
        const {
            __scopeDropdownMenu: n,
            ...r
        } = e, o = de(n);
        return u.createElement(I1, V({}, o, r, {
            ref: t
        }))
    }),
    tv = e => {
        const {
            __scopeDropdownMenu: t,
            children: n,
            open: r,
            onOpenChange: o,
            defaultOpen: s
        } = e, i = de(t), [a = !1, c] = ir({
            prop: r,
            defaultProp: s,
            onChange: o
        });
        return u.createElement(V1, V({}, i, {
            open: a,
            onOpenChange: c
        }), n)
    },
    nv = u.forwardRef((e, t) => {
        const {
            __scopeDropdownMenu: n,
            ...r
        } = e, o = de(n);
        return u.createElement(N1, V({}, o, r, {
            ref: t
        }))
    }),
    rv = u.forwardRef((e, t) => {
        const {
            __scopeDropdownMenu: n,
            ...r
        } = e, o = de(n);
        return u.createElement(F1, V({}, o, r, {
            ref: t,
            style: {
                ...e.style,
                "--radix-dropdown-menu-content-transform-origin": "var(--radix-popper-transform-origin)",
                "--radix-dropdown-menu-content-available-width": "var(--radix-popper-available-width)",
                "--radix-dropdown-menu-content-available-height": "var(--radix-popper-available-height)",
                "--radix-dropdown-menu-trigger-width": "var(--radix-popper-anchor-width)",
                "--radix-dropdown-menu-trigger-height": "var(--radix-popper-anchor-height)"
            }
        }))
    }),
    ov = U1,
    sv = H1,
    iv = G1,
    gl = z1,
    vl = Y1,
    yl = X1,
    bl = Z1,
    av = q1,
    xl = Q1,
    wl = J1,
    Cl = ev,
    cv = tv,
    Al = nv,
    $l = rv,
    lv = ov,
    uv = sv,
    Di = iv,
    fv = cv,
    dv = av,
    Pl = u.forwardRef(({
        className: e,
        inset: t,
        children: n,
        ...r
    }, o) => M.jsxs(Al, {
        ref: o,
        className: ke("flex cursor-default select-none items-center rounded-sm px-2 py-1.5 text-sm outline-none focus:bg-accent data-[state=open]:bg-accent", t && "pl-8", e),
        ...r,
        children: [n, M.jsx(pp, {
            className: "ml-auto h-4 w-4"
        })]
    }));
Pl.displayName = Al.displayName;
const Sl = u.forwardRef(({
    className: e,
    ...t
}, n) => M.jsx($l, {
    ref: n,
    className: ke("z-50 min-w-[8rem] overflow-hidden rounded-md border bg-popover p-1 text-popover-foreground shadow-lg data-[state=open]:animate-in data-[state=closed]:animate-out data-[state=closed]:fade-out-0 data-[state=open]:fade-in-0 data-[state=closed]:zoom-out-95 data-[state=open]:zoom-in-95 data-[side=bottom]:slide-in-from-top-2 data-[side=left]:slide-in-from-right-2 data-[side=right]:slide-in-from-left-2 data-[side=top]:slide-in-from-bottom-2", e),
    ...t
}));
Sl.displayName = $l.displayName;
const El = u.forwardRef(({
    className: e,
    sideOffset: t = 4,
    ...n
}, r) => M.jsx(gl, {
    ref: r,
    sideOffset: t,
    className: ke("z-50 min-w-[8rem] overflow-hidden rounded-md border bg-popover p-1 text-popover-foreground shadow-md", "data-[state=open]:animate-in data-[state=closed]:animate-out data-[state=closed]:fade-out-0 data-[state=open]:fade-in-0 data-[state=closed]:zoom-out-95 data-[state=open]:zoom-in-95 data-[side=bottom]:slide-in-from-top-2 data-[side=left]:slide-in-from-right-2 data-[side=right]:slide-in-from-left-2 data-[side=top]:slide-in-from-bottom-2", e),
    ...n
}));
El.displayName = gl.displayName;
const hv = u.forwardRef(({
    className: e,
    inset: t,
    ...n
}, r) => M.jsx(yl, {
    ref: r,
    className: ke("relative flex cursor-default select-none items-center rounded-sm px-2 py-1.5 text-sm outline-none transition-colors focus:bg-accent focus:text-accent-foreground data-[disabled]:pointer-events-none data-[disabled]:opacity-50", t && "pl-8", e),
    ...n
}));
hv.displayName = yl.displayName;
const Tl = u.forwardRef(({
    className: e,
    children: t,
    checked: n,
    ...r
}, o) => M.jsxs(bl, {
    ref: o,
    className: ke("relative flex cursor-default select-none items-center rounded-sm py-1.5 pl-8 pr-2 text-sm outline-none transition-colors focus:bg-accent focus:text-accent-foreground data-[disabled]:pointer-events-none data-[disabled]:opacity-50", e),
    checked: n,
    ...r,
    children: [M.jsx("span", {
        className: "absolute left-2 flex h-3.5 w-3.5 items-center justify-center",
        children: M.jsx(wl, {
            children: M.jsx(dp, {
                className: "h-4 w-4"
            })
        })
    }), t]
}));
Tl.displayName = bl.displayName;
const Ml = u.forwardRef(({
    className: e,
    children: t,
    ...n
}, r) => M.jsxs(xl, {
    ref: r,
    className: ke("relative flex cursor-default select-none items-center rounded-sm py-1.5 pl-8 pr-2 text-sm outline-none transition-colors focus:bg-accent focus:text-accent-foreground data-[disabled]:pointer-events-none data-[disabled]:opacity-50", e),
    ...n,
    children: [M.jsx("span", {
        className: "absolute left-2 flex h-3.5 w-3.5 items-center justify-center",
        children: M.jsx(wl, {
            children: M.jsx(xp, {
                className: "h-4 w-4 fill-current"
            })
        })
    }), t]
}));
Ml.displayName = xl.displayName;
const pv = u.forwardRef(({
    className: e,
    inset: t,
    ...n
}, r) => M.jsx(vl, {
    ref: r,
    className: ke("px-2 py-1.5 text-sm font-semibold", t && "pl-8", e),
    ...n
}));
pv.displayName = vl.displayName;
const mv = u.forwardRef(({
    className: e,
    ...t
}, n) => M.jsx(Cl, {
    ref: n,
    className: ke("-mx-1 my-1 h-px bg-muted", e),
    ...t
}));
mv.displayName = Cl.displayName;
const gv = u.forwardRef((e, t) => u.createElement(ue.span, V({}, e, {
        ref: t,
        style: {
            position: "absolute",
            border: 0,
            width: 1,
            height: 1,
            padding: 0,
            margin: -1,
            overflow: "hidden",
            clip: "rect(0, 0, 0, 0)",
            whiteSpace: "nowrap",
            wordWrap: "normal",
            ...e.style
        }
    }))),
    vv = gv,
    [hr, ny] = Lt("Tooltip", [lr]),
    Yo = lr(),
    yv = "TooltipProvider",
    bv = 700,
    lo = "tooltip.open",
    [xv, Xo] = hr(yv),
    wv = e => {
        const {
            __scopeTooltip: t,
            delayDuration: n = bv,
            skipDelayDuration: r = 300,
            disableHoverableContent: o = !1,
            children: s
        } = e, [i, a] = u.useState(!0), c = u.useRef(!1), l = u.useRef(0);
        return u.useEffect(() => {
            const f = l.current;
            return () => window.clearTimeout(f)
        }, []), u.createElement(xv, {
            scope: t,
            isOpenDelayed: i,
            delayDuration: n,
            onOpen: u.useCallback(() => {
                window.clearTimeout(l.current), a(!1)
            }, []),
            onClose: u.useCallback(() => {
                window.clearTimeout(l.current), l.current = window.setTimeout(() => a(!0), r)
            }, [r]),
            isPointerInTransitRef: c,
            onPointerInTransitChange: u.useCallback(f => {
                c.current = f
            }, []),
            disableHoverableContent: o
        }, s)
    },
    Zo = "Tooltip",
    [Cv, pr] = hr(Zo),
    Av = e => {
        const {
            __scopeTooltip: t,
            children: n,
            open: r,
            defaultOpen: o = !1,
            onOpenChange: s,
            disableHoverableContent: i,
            delayDuration: a
        } = e, c = Xo(Zo, e.__scopeTooltip), l = Yo(t), [f, d] = u.useState(null), h = _t(), p = u.useRef(0), g = i ?? c.disableHoverableContent, m = a ?? c.delayDuration, b = u.useRef(!1), [x = !1, v] = ir({
            prop: r,
            defaultProp: o,
            onChange: w => {
                w ? (c.onOpen(), document.dispatchEvent(new CustomEvent(lo))) : c.onClose(), s?.(w)
            }
        }), y = u.useMemo(() => x ? b.current ? "delayed-open" : "instant-open" : "closed", [x]), C = u.useCallback(() => {
            window.clearTimeout(p.current), b.current = !1, v(!0)
        }, [v]), $ = u.useCallback(() => {
            window.clearTimeout(p.current), v(!1)
        }, [v]), A = u.useCallback(() => {
            window.clearTimeout(p.current), p.current = window.setTimeout(() => {
                b.current = !0, v(!0)
            }, m)
        }, [m, v]);
        return u.useEffect(() => () => window.clearTimeout(p.current), []), u.createElement(Uo, l, u.createElement(Cv, {
            scope: t,
            contentId: h,
            open: x,
            stateAttribute: y,
            trigger: f,
            onTriggerChange: d,
            onTriggerEnter: u.useCallback(() => {
                c.isOpenDelayed ? A() : C()
            }, [c.isOpenDelayed, A, C]),
            onTriggerLeave: u.useCallback(() => {
                g ? $() : window.clearTimeout(p.current)
            }, [$, g]),
            onOpen: C,
            onClose: $,
            disableHoverableContent: g
        }, n))
    },
    Ri = "TooltipTrigger",
    $v = u.forwardRef((e, t) => {
        const {
            __scopeTooltip: n,
            ...r
        } = e, o = pr(Ri, n), s = Xo(Ri, n), i = Yo(n), a = u.useRef(null), c = le(t, a, o.onTriggerChange), l = u.useRef(!1), f = u.useRef(!1), d = u.useCallback(() => l.current = !1, []);
        return u.useEffect(() => () => document.removeEventListener("pointerup", d), [d]), u.createElement(Bc, V({
            asChild: !0
        }, i), u.createElement(ue.button, V({
            "aria-describedby": o.open ? o.contentId : void 0,
            "data-state": o.stateAttribute,
            id: "start"
        }, r, {
            ref: c,
            onPointerMove: K(e.onPointerMove, h => {
                h.pointerType !== "touch" && !f.current && !s.isPointerInTransitRef.current && (o.onTriggerEnter(), f.current = !0)
            }),
            onPointerLeave: K(e.onPointerLeave, () => {
                o.onTriggerLeave(), f.current = !1
            }),
            onPointerDown: K(e.onPointerDown, () => {
                l.current = !0, document.addEventListener("pointerup", d, {
                    once: !0
                })
            }),
            onFocus: K(e.onFocus, () => {
                l.current || o.onOpen()
            }),
            onBlur: K(e.onBlur, o.onClose),
            onClick: K(e.onClick, o.onClose)
        })))
    }),
    Pv = "TooltipPortal",
    [ry, Sv] = hr(Pv, {
        forceMount: void 0
    }),
    rn = "TooltipContent",
    Ev = u.forwardRef((e, t) => {
        const n = Sv(rn, e.__scopeTooltip),
            {
                forceMount: r = n.forceMount,
                side: o = "top",
                ...s
            } = e,
            i = pr(rn, e.__scopeTooltip);
        return u.createElement(Nt, {
            present: r || i.open
        }, i.disableHoverableContent ? u.createElement(Dl, V({
            side: o
        }, s, {
            ref: t
        })) : u.createElement(Tv, V({
            side: o
        }, s, {
            ref: t
        })))
    }),
    Tv = u.forwardRef((e, t) => {
        const n = pr(rn, e.__scopeTooltip),
            r = Xo(rn, e.__scopeTooltip),
            o = u.useRef(null),
            s = le(t, o),
            [i, a] = u.useState(null),
            {
                trigger: c,
                onClose: l
            } = n,
            f = o.current,
            {
                onPointerInTransitChange: d
            } = r,
            h = u.useCallback(() => {
                a(null), d(!1)
            }, [d]),
            p = u.useCallback((g, m) => {
                const b = g.currentTarget,
                    x = {
                        x: g.clientX,
                        y: g.clientY
                    },
                    v = Dv(x, b.getBoundingClientRect()),
                    y = Rv(x, v),
                    C = Ov(m.getBoundingClientRect()),
                    $ = kv([...y, ...C]);
                a($), d(!0)
            }, [d]);
        return u.useEffect(() => () => h(), [h]), u.useEffect(() => {
            if (c && f) {
                const g = b => p(b, f),
                    m = b => p(b, c);
                return c.addEventListener("pointerleave", g), f.addEventListener("pointerleave", m), () => {
                    c.removeEventListener("pointerleave", g), f.removeEventListener("pointerleave", m)
                }
            }
        }, [c, f, p, h]), u.useEffect(() => {
            if (i) {
                const g = m => {
                    const b = m.target,
                        x = {
                            x: m.clientX,
                            y: m.clientY
                        },
                        v = c?.contains(b) || f?.contains(b),
                        y = !_v(x, i);
                    v ? h() : y && (h(), l())
                };
                return document.addEventListener("pointermove", g), () => document.removeEventListener("pointermove", g)
            }
        }, [c, f, i, l, h]), u.createElement(Dl, V({}, e, {
            ref: s
        }))
    }),
    [Mv, oy] = hr(Zo, {
        isInside: !1
    }),
    Dl = u.forwardRef((e, t) => {
        const {
            __scopeTooltip: n,
            children: r,
            "aria-label": o,
            onEscapeKeyDown: s,
            onPointerDownOutside: i,
            ...a
        } = e, c = pr(rn, n), l = Yo(n), {
            onClose: f
        } = c;
        return u.useEffect(() => (document.addEventListener(lo, f), () => document.removeEventListener(lo, f)), [f]), u.useEffect(() => {
            if (c.trigger) {
                const d = h => {
                    const p = h.target;
                    p != null && p.contains(c.trigger) && f()
                };
                return window.addEventListener("scroll", d, {
                    capture: !0
                }), () => window.removeEventListener("scroll", d, {
                    capture: !0
                })
            }
        }, [c.trigger, f]), u.createElement($c, {
            asChild: !0,
            disableOutsidePointerEvents: !1,
            onEscapeKeyDown: s,
            onPointerDownOutside: i,
            onFocusOutside: d => d.preventDefault(),
            onDismiss: f
        }, u.createElement(Uc, V({
            "data-state": c.stateAttribute
        }, l, a, {
            ref: t,
            style: {
                ...a.style,
                "--radix-tooltip-content-transform-origin": "var(--radix-popper-transform-origin)",
                "--radix-tooltip-content-available-width": "var(--radix-popper-available-width)",
                "--radix-tooltip-content-available-height": "var(--radix-popper-available-height)",
                "--radix-tooltip-trigger-width": "var(--radix-popper-anchor-width)",
                "--radix-tooltip-trigger-height": "var(--radix-popper-anchor-height)"
            }
        }), u.createElement(xc, null, r), u.createElement(Mv, {
            scope: n,
            isInside: !0
        }, u.createElement(vv, {
            id: c.contentId,
            role: "tooltip"
        }, o || r))))
    });

function Dv(e, t) {
    const n = Math.abs(t.top - e.y),
        r = Math.abs(t.bottom - e.y),
        o = Math.abs(t.right - e.x),
        s = Math.abs(t.left - e.x);
    switch (Math.min(n, r, o, s)) {
        case s:
            return "left";
        case o:
            return "right";
        case n:
            return "top";
        case r:
            return "bottom";
        default:
            throw new Error("unreachable")
    }
}

function Rv(e, t, n = 5) {
    const r = [];
    switch (t) {
        case "top":
            r.push({
                x: e.x - n,
                y: e.y + n
            }, {
                x: e.x + n,
                y: e.y + n
            });
            break;
        case "bottom":
            r.push({
                x: e.x - n,
                y: e.y - n
            }, {
                x: e.x + n,
                y: e.y - n
            });
            break;
        case "left":
            r.push({
                x: e.x + n,
                y: e.y - n
            }, {
                x: e.x + n,
                y: e.y + n
            });
            break;
        case "right":
            r.push({
                x: e.x - n,
                y: e.y - n
            }, {
                x: e.x - n,
                y: e.y + n
            });
            break
    }
    return r
}

function Ov(e) {
    const {
        top: t,
        right: n,
        bottom: r,
        left: o
    } = e;
    return [{
        x: o,
        y: t
    }, {
        x: n,
        y: t
    }, {
        x: n,
        y: r
    }, {
        x: o,
        y: r
    }]
}

function _v(e, t) {
    const {
        x: n,
        y: r
    } = e;
    let o = !1;
    for (let s = 0, i = t.length - 1; s < t.length; i = s++) {
        const a = t[s].x,
            c = t[s].y,
            l = t[i].x,
            f = t[i].y;
        c > r != f > r && n < (l - a) * (r - c) / (f - c) + a && (o = !o)
    }
    return o
}

function kv(e) {
    const t = e.slice();
    return t.sort((n, r) => n.x < r.x ? -1 : n.x > r.x ? 1 : n.y < r.y ? -1 : n.y > r.y ? 1 : 0), Lv(t)
}

function Lv(e) {
    if (e.length <= 1) return e.slice();
    const t = [];
    for (let r = 0; r < e.length; r++) {
        const o = e[r];
        for (; t.length >= 2;) {
            const s = t[t.length - 1],
                i = t[t.length - 2];
            if ((s.x - i.x) * (o.y - i.y) >= (s.y - i.y) * (o.x - i.x)) t.pop();
            else break
        }
        t.push(o)
    }
    t.pop();
    const n = [];
    for (let r = e.length - 1; r >= 0; r--) {
        const o = e[r];
        for (; n.length >= 2;) {
            const s = n[n.length - 1],
                i = n[n.length - 2];
            if ((s.x - i.x) * (o.y - i.y) >= (s.y - i.y) * (o.x - i.x)) n.pop();
            else break
        }
        n.push(o)
    }
    return n.pop(), t.length === 1 && n.length === 1 && t[0].x === n[0].x && t[0].y === n[0].y ? t : t.concat(n)
}
const Iv = wv,
    Vv = Av,
    Nv = $v,
    Rl = Ev,
    Oi = Iv,
    _i = Vv,
    ki = Nv,
    uo = u.forwardRef(({
        className: e,
        sideOffset: t = 4,
        ...n
    }, r) => M.jsx(Rl, {
        ref: r,
        sideOffset: t,
        className: ke("z-50 overflow-hidden rounded-md bg-foreground px-3 py-1.5 text-xs text-background animate-in fade-in-0 zoom-in-95 data-[state=closed]:animate-out data-[state=closed]:fade-out-0 data-[state=closed]:zoom-out-95 data-[side=bottom]:slide-in-from-top-2 data-[side=left]:slide-in-from-right-2 data-[side=right]:slide-in-from-left-2 data-[side=top]:slide-in-from-bottom-2", e),
        ...n
    }));
uo.displayName = Rl.displayName;

function Fv({
    appRoot: e,
    onClose: t
}) {
    const {
        audio: n,
        showKeystrokes: r,
        scrollbarHidden: o,
        recordingMode: s,
        countdown: i,
        showMouseClicks: a
    } = re(m => ({
        audio: m.audio,
        showKeystrokes: m.showKeystrokes,
        scrollbarHidden: m.scrollbarHidden,
        recordingMode: m.recordingMode,
        countdown: m.countdown,
        showMouseClicks: m.showMouseClicks
    })), c = [{
        name: "area",
        label: "Area",
        icon: M.jsx(Cp, {
            className: "h-5 w-5"
        })
    }, {
        name: "tab",
        label: "Current Tab",
        icon: M.jsx($p, {
            className: "h-5 w-5"
        })
    }, {
        name: "desktop",
        label: "Desktop",
        icon: M.jsx(yp, {
            className: "h-5 w-5"
        })
    }], l = [{
        name: "audio",
        label: "Enable Audio",
        checked: n,
        onCheckedChange: m => re.setState({
            audio: m
        })
    }, {
        name: "showKeystrokes",
        label: "Show Keystrokes",
        checked: r,
        onCheckedChange: m => re.setState({
            showKeystrokes: m
        })
    }, {
        name: "showMouseClicks",
        label: "Show Mouse Clicks",
        checked: a,
        onCheckedChange: m => re.setState({
            showMouseClicks: m
        })
    }, {
        name: "scrollbarHidden",
        label: "Hide Scrollbar",
        checked: o,
        onCheckedChange: m => re.setState({
            scrollbarHidden: m
        })
    }];

    function f() {
        switch (s) {
            case "area":
            case "tab":
                re.setState({
                    showCountdown: !0
                });
                break;
            default:
                chrome.runtime.sendMessage({
                    type: "start-recording",
                    target: "background",
                    data: {
                        recordingMode: s
                    }
                }), t();
                break
        }
    }
    const d = u.useRef(null),
        h = u.useRef(null),
        [p, g] = u.useState();
    return u.useEffect(() => {
        const m = h.current;
        m && g(m.getBoundingClientRect())
    }, []), M.jsx(Am, {
        cancel: "button",
        nodeRef: d,
        bounds: {
            bottom: 16,
            left: -(window.innerWidth / 2 - (p?.width ?? 0) / 2),
            right: window.innerWidth / 2 - (p?.width ?? 0) / 2,
            top: -(window.innerHeight - (p?.height ?? 0) - 16)
        },
        children: M.jsx("div", {
            ref: d,
            className: "fixed bottom-4 left-1/2 z-[2147483646]",
            children: M.jsxs(Jt.div, {
                className: "flex space-x-2 rounded-xl bg-background/50 p-1.5 shadow-[0_1px_2px_0px_rgb(0_0_0_/0.1),0_-1px_2px_-1px_rgb(0_0_0_/0.1)] backdrop-blur",
                ref: h,
                initial: {
                    opacity: 0,
                    y: 20,
                    x: "-50%"
                },
                animate: {
                    opacity: 1,
                    y: 0,
                    x: "-50%"
                },
                exit: {
                    opacity: 0,
                    y: 60,
                    x: "-50%"
                },
                children: [M.jsx("div", {
                    className: "flex items-center",
                    children: M.jsx(Ht, {
                        variant: "ghost",
                        size: "sm",
                        onClick: t,
                        children: M.jsx(gp, {
                            className: "h-5 w-5"
                        })
                    })
                }), M.jsxs("div", {
                    className: "flex items-center space-x-2",
                    children: [M.jsx(Fr, {}), c.map(m => M.jsx(Oi, {
                        children: M.jsxs(_i, {
                            children: [M.jsx(ki, {
                                asChild: !0,
                                children: M.jsx(Ht, {
                                    variant: "ghost",
                                    size: "sm",
                                    onClick: () => re.setState({
                                        recordingMode: m.name
                                    }),
                                    className: Tt({
                                        "cursor-default text-green-500 hover:bg-transparent hover:text-green-500": m.name === s
                                    }),
                                    children: m.icon
                                })
                            }), M.jsx(uo, {
                                children: M.jsx("p", {
                                    children: m.label
                                })
                            })]
                        })
                    }, m.name))]
                }), M.jsxs("div", {
                    className: "flex items-center space-x-2",
                    children: [M.jsx(Fr, {}), M.jsxs(lv, {
                        modal: !1,
                        children: [M.jsx(uv, {
                            asChild: !0,
                            children: M.jsxs(Ht, {
                                variant: "ghost",
                                size: "sm",
                                className: "inline-flex select-none items-center space-x-1",
                                disabled: !Nl.includes(s),
                                children: [M.jsx("span", {
                                    children: "Options"
                                }), M.jsx(up, {
                                    className: "h-5 w-5"
                                })]
                            })
                        }), M.jsx(Di, {
                            container: e,
                            children: M.jsxs(El, {
                                className: "z-[2147483647] w-52 rounded-md bg-background/60 backdrop-blur",
                                sideOffset: 10,
                                side: "top",
                                onCloseAutoFocus: m => m.preventDefault(),
                                children: [l.map(m => M.jsx(Tl, {
                                    checked: m.checked,
                                    onCheckedChange: m.onCheckedChange,
                                    children: m.label
                                }, m.name)), M.jsxs(fv, {
                                    children: [M.jsx(Pl, {
                                        children: M.jsx("div", {
                                            className: "pl-6",
                                            children: "Countdown"
                                        })
                                    }), M.jsx(Di, {
                                        container: e,
                                        children: M.jsx(Sl, {
                                            className: "z-[2147483647] min-w-[5rem] rounded-md bg-background/60 tabular-nums backdrop-blur",
                                            sideOffset: 4,
                                            children: M.jsx(dv, {
                                                value: i.toString(),
                                                onValueChange: m => re.setState({
                                                    countdown: parseInt(m)
                                                }),
                                                children: [0, 1, 3, 5, 7, 10].map(m => M.jsxs(Ml, {
                                                    value: m.toString(),
                                                    children: [m, "s"]
                                                }, `countdown-${m}`))
                                            })
                                        })
                                    })]
                                })]
                            })
                        })]
                    })]
                }), M.jsxs("div", {
                    className: "flex items-center space-x-2",
                    children: [M.jsx(Fr, {}), M.jsx(Oi, {
                        children: M.jsxs(_i, {
                            children: [M.jsx(ki, {
                                asChild: !0,
                                children: M.jsx(Ht, {
                                    variant: "ghost",
                                    size: "sm",
                                    onClick: f,
                                    className: "select-none",
                                    children: "Start"
                                })
                            }), M.jsx(uo, {
                                children: M.jsx("p", {
                                    children: "Start Recording"
                                })
                            })]
                        })
                    })]
                })]
            })
        })
    })
}

function Fr() {
    return M.jsx("div", {
        className: "h-2/3 border border-slate-950"
    })
}

function jv({
    count: e,
    onFinish: t
}) {
    const [n, r] = u.useState(e), o = u.useRef();
    return u.useEffect(() => {
        const s = setInterval(() => {
            r(i => i - 1)
        }, 1e3);
        return o.current = s, () => clearInterval(s)
    }, [t]), u.useEffect(() => {
        n === 0 && (clearInterval(o.current), t())
    }, [n, t]), M.jsx("div", {
        className: "fixed inset-0 z-[2147483647] flex items-center justify-center bg-background/30 backdrop-blur",
        children: n > 0 && M.jsx(Jt.div, {
            className: "absolute select-none text-foreground",
            initial: {
                scale: 1
            },
            animate: {
                scale: 20,
                transition: {
                    ease: "easeOut"
                }
            },
            children: n
        }, n)
    })
}

function Bv() {
    const [e, t] = u.useState({
        x: 0,
        y: 0
    }), [n, r] = u.useState(!1);

    function o(a) {
        t({
            x: a.clientX,
            y: a.clientY
        }), r(!0)
    }
    const s = u.useCallback(a => {
        n && t({
            x: a.clientX,
            y: a.clientY
        })
    }, [n]);

    function i(a) {
        r(!1)
    }
    return u.useEffect(() => (document.addEventListener("pointerdown", o), document.addEventListener("pointerup", i), document.addEventListener("pointermove", s), document.addEventListener("dragstart", i), () => {
        document.removeEventListener("pointerdown", o), document.removeEventListener("pointerup", i), document.removeEventListener("pointermove", s), document.removeEventListener("dragstart", i)
    }), [s]), M.jsx(Oo, {
        children: n && M.jsx(Jt.div, {
            className: "pointer-events-none fixed z-[2147483647] h-4 w-4 rounded-full bg-primary/60",
            style: {
                left: e.x,
                top: e.y
            },
            initial: {
                scale: 0,
                opacity: 0,
                translateX: "-50%",
                translateY: "-50%"
            },
            animate: {
                scale: 2,
                opacity: 1,
                transition: {
                    duration: 0
                }
            },
            exit: {
                scale: 0,
                opacity: 0,
                transition: {
                    duration: .3,
                    ease: "linear"
                }
            }
        })
    })
}
const Me = 0,
    Uv = () => {
        const {
            isRecording: e,
            area: t
        } = re(w => ({
            area: w.area,
            isRecording: w.isRecording
        })), [n, r] = u.useState({
            x: t?.x,
            y: t?.y
        }), [o, s] = u.useState({
            x: t.x + t.width,
            y: t.y + t.height
        }), [i, a] = u.useState(!1), [c, l] = u.useState(!1), [f, d] = u.useState({
            x: 0,
            y: 0
        });

        function h(w) {
            w.button === 0 && (l(!0), d({
                x: w.clientX,
                y: w.clientY
            }))
        }

        function p(w) {
            c && (r({
                x: n.x + w.clientX - f.x,
                y: n.y + w.clientY - f.y
            }), s({
                x: o.x + w.clientX - f.x,
                y: o.y + w.clientY - f.y
            }), d({
                x: w.clientX,
                y: w.clientY
            }))
        }

        function g(w) {
            l(!1), re.setState({
                area: {
                    x: Math.min(n.x, o.x),
                    y: Math.min(n.y, o.y),
                    width: Math.abs(n.x - o.x),
                    height: Math.abs(n.y - o.y)
                }
            })
        }

        function m(w) {
            w.button === 0 && (r({
                x: w.clientX,
                y: w.clientY
            }), s({
                x: w.clientX,
                y: w.clientY
            }), a(!0))
        }

        function b(w) {
            i && s({
                x: w.clientX,
                y: w.clientY
            }), c && (r({
                x: n.x + w.clientX - f.x,
                y: n.y + w.clientY - f.y
            }), s({
                x: o.x + w.clientX - f.x,
                y: o.y + w.clientY - f.y
            }), d({
                x: w.clientX,
                y: w.clientY
            }))
        }

        function x() {
            a(!1), l(!1), re.setState({
                area: {
                    x: Math.min(n.x, o.x),
                    y: Math.min(n.y, o.y),
                    width: Math.abs(n.x - o.x),
                    height: Math.abs(n.y - o.y)
                }
            })
        }
        const v = Math.min(n.x, o.x),
            y = Math.min(n.y, o.y),
            C = Math.max(n.x, o.x),
            $ = Math.max(n.y, o.y);

        function A(w) {
            w.preventDefault(), r({
                x: 0,
                y: 0
            }), s({
                x: 0,
                y: 0
            })
        }
        return M.jsxs(M.Fragment, {
            children: [M.jsx("div", {
                className: Tt("fixed inset-0 z-[2147483645]", {
                    "pointer-events-none": e
                }),
                onPointerDown: m,
                onPointerMove: b,
                onPointerUp: x,
                onContextMenu: A,
                style: {
                    clipPath: i ? void 0 : `polygon(0px 0px, 0px 100%, 100% 100%, 100% 0px, 0px 0px, ${v}px ${y}px, ${C}px ${y}px, ${C}px ${$}px, ${v}px ${$}px, ${v}px ${y}px, 0px 0px)`
                },
                children: M.jsx("div", {
                    className: "h-full w-full cursor-crosshair bg-foreground/60",
                    style: {
                        clipPath: `polygon(0px 0px, 0px 100%, 100% 100%, 100% 0px, 0px 0px, ${v-Me}px ${y-Me}px, ${C+Me}px ${y-Me}px, ${C+Me}px ${$+Me}px, ${v-Me}px ${$+Me}px, ${v-Me}px ${y-Me}px, 0px 0px)`
                    }
                })
            }), M.jsx("div", {
                className: Tt("fixed left-0 top-0 z-[2147483645] outline-dashed outline-2 outline-background", {
                    "pointer-events-none": i || e,
                    "cursor-grab": !c,
                    "pointer-events-auto cursor-grabbing": c
                }),
                onPointerDown: h,
                onPointerMove: p,
                onPointerUp: g,
                onDragStart: w => {
                    w.preventDefault()
                },
                style: {
                    transform: `translate(${v}px, ${y}px)`,
                    width: `${C-v}px`,
                    height: `${$-y}px`
                }
            })]
        })
    },
    Wv = Bl() ? "⌘" : Ul() ? "⊞" : "Meta";

function Hv() {
    const {
        recordingMode: e,
        area: t
    } = re(a => ({
        recordingMode: a.recordingMode,
        area: a.area
    })), [n, r] = u.useState([]), o = u.useCallback(a => {
        r(c => c.includes(a.code) ? c : [...c, a.code])
    }, []), s = u.useCallback(a => {
        r(c => c.filter(l => l !== a.code))
    }, []);

    function i() {
        r([])
    }
    return u.useEffect(() => (window.addEventListener("keydown", o), window.addEventListener("keyup", s), window.addEventListener("focus", i), () => {
        window.removeEventListener("keydown", o), window.removeEventListener("keyup", s), window.removeEventListener("focus", i)
    }), [o, s]), M.jsx("div", {
        className: Tt("fixed z-[2147483647] flex -translate-x-1/2 justify-center", {
            "bottom-8 left-1/2": ["desktop", "tab"].includes(e)
        }),
        style: {
            bottom: e === "area" ? window.innerHeight - t.y - t.height + 8 : void 0,
            left: e === "area" ? t.x + t.width / 2 : void 0
        },
        children: M.jsx(Oo, {
            children: n.length > 0 && M.jsx(Jt.div, {
                className: "flex items-center space-x-2",
                animate: {
                    opacity: 1
                },
                exit: {
                    opacity: 0,
                    transition: {
                        delay: .3
                    }
                },
                children: n.map((a, c) => M.jsx(Jt.kbd, {
                    className: Tt("select-none rounded-lg border bg-background/30 px-4 py-2 text-3xl font-semibold text-foreground shadow-[0_2px_0px_1px_hsl(214.3_31.8%_91.4%)] backdrop-blur", {
                        "h-[54px] w-48": a === "Space"
                    }),
                    animate: {
                        opacity: 1
                    },
                    children: a.startsWith("Meta") ? Wv : Fl[a] ?? a
                }, `${a}-${c}`))
            })
        })
    })
}
const Xt = document.createElement("style");
document.head.appendChild(Xt);

function Gv() {
    Xt.sheet?.insertRule(`
    ::-webkit-scrollbar {
      display: none;
    }
  `, 0)
}

function Kv() {
    Xt.sheet?.cssRules.length && Xt.sheet?.cssRules.length > 0 && Xt.sheet?.deleteRule(0)
}

function zv({
    scrollbarHidden: e,
    isRecording: t
}) {
    u.useEffect(() => (e && t && Gv(), () => {
        Kv()
    }), [t, e])
}

function Yv({
    appRoot: e
}) {
    const {
        scrollbarHidden: t,
        audio: n,
        showKeystrokes: r,
        recordingMode: o,
        showCountdown: s,
        isRecording: i,
        countdown: a,
        area: c,
        showMouseClicks: l,
        showControlbar: f
    } = re(p => ({
        scrollbarHidden: p.scrollbarHidden,
        audio: p.audio,
        showKeystrokes: p.showKeystrokes,
        recordingMode: p.recordingMode,
        showCountdown: p.showCountdown,
        isRecording: p.isRecording,
        countdown: p.countdown,
        area: p.area,
        showMouseClicks: p.showMouseClicks,
        showControlbar: p.showControlbar
    }));
    zv({
        isRecording: i,
        scrollbarHidden: t
    });
    const d = u.useCallback(() => {
        re.setState({
            showCountdown: !1,
            showControlbar: !1
        });
        const p = 100;
        Wl(!0), setTimeout(() => {
            chrome.runtime.sendMessage({
                type: "start-recording",
                target: "background",
                data: {
                    audio: n,
                    recordingMode: o,
                    ...o === "area" ? {
                        area: c
                    } : {}
                }
            })
        }, p)
    }, [n, o, c]);

    function h() {
        re.setState({
            showControlbar: !1
        })
    }
    return M.jsxs(M.Fragment, {
        children: [s && M.jsx(jv, {
            count: a,
            onFinish: d
        }, a), i && r && M.jsx(Hv, {}), M.jsx(Oo, {
            children: f && !i && M.jsx(Fv, {
                appRoot: e,
                onClose: h
            })
        }), o === "area" && (i || f) && M.jsx(Uv, {}), l && i && M.jsx(Bv, {})]
    })
}
kl({
    dsn: "https://d12dd277a192c6ca69ba59ebb958e6e2@o82598.ingest.sentry.io/4505787043479552"
});
chrome.runtime.onMessage.addListener((e, t, n) => {
    switch (e.type) {
        case "show-controlbar":
            re.setState({
                showControlbar: !0
            });
            break;
        case "stop-recording":
            re.setState({
                isRecording: !1
            });
            break
    }
});
Kl(["assets/sdk-b7a746d2.css"], e => {
    Ll.createRoot(e).render(M.jsx(me.StrictMode, {
        children: M.jsx(Yv, {
            appRoot: e
        })
    }))
});
//# sourceMappingURL=main-bb129543.js.map