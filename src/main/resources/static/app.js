/* ============================================================
   Random Data API Documentation — app.js
   Handles: sidebar toggle, lang tabs, copy buttons,
            active nav tracking, live API tester, status check
   ============================================================ */

const BASE_URL = 'https://random-data-api-vvy8.onrender.com';

/* ============================
   DOM Ready
   ============================ */
document.addEventListener('DOMContentLoaded', () => {
  initSidebar();
  initLangTabs();
  initCopyButtons();
  initActiveNavTracking();
  initLiveInputPreviews();
  checkApiStatus();
});

/* ============================
   Sidebar Toggle (mobile)
   ============================ */
function initSidebar() {
  const sidebar      = document.getElementById('sidebar');
  const topbarMenu   = document.getElementById('topbarMenu');
  const sidebarToggle = document.getElementById('sidebarToggle');
  const mainContent  = document.getElementById('mainContent');

  function openSidebar()  { sidebar.classList.add('open'); }
  function closeSidebar() { sidebar.classList.remove('open'); }

  topbarMenu?.addEventListener('click', () => {
    sidebar.classList.toggle('open');
  });

  sidebarToggle?.addEventListener('click', closeSidebar);

  // Close when clicking a nav link (mobile)
  document.querySelectorAll('.nav-link').forEach(link => {
    link.addEventListener('click', () => {
      if (window.innerWidth <= 768) closeSidebar();
    });
  });

  // Close when clicking outside
  mainContent?.addEventListener('click', () => {
    if (window.innerWidth <= 768) closeSidebar();
  });
}

/* ============================
   Language Tabs
   ============================ */
function initLangTabs() {
  const tabs   = document.querySelectorAll('.lang-tab');
  const panels = document.querySelectorAll('.lang-panel');

  tabs.forEach(tab => {
    tab.addEventListener('click', () => {
      const lang = tab.dataset.lang;

      tabs.forEach(t => t.classList.remove('active'));
      panels.forEach(p => p.classList.remove('active'));

      tab.classList.add('active');
      const panel = document.getElementById(`panel-${lang}`);
      if (panel) panel.classList.add('active');
    });
  });
}

/* ============================
   Copy Buttons
   ============================ */
function initCopyButtons() {
  document.querySelectorAll('.copy-btn').forEach(btn => {
    btn.addEventListener('click', async () => {
      const text = btn.dataset.copy || getParentCode(btn);
      if (!text) return;

      try {
        await navigator.clipboard.writeText(text);
        btn.textContent = 'Copied!';
        btn.classList.add('copied');
        setTimeout(() => {
          btn.textContent = 'Copy';
          btn.classList.remove('copied');
        }, 2000);
      } catch {
        // Fallback for older browsers
        const ta = document.createElement('textarea');
        ta.value = text;
        ta.style.position = 'fixed';
        ta.style.opacity = '0';
        document.body.appendChild(ta);
        ta.select();
        document.execCommand('copy');
        document.body.removeChild(ta);
        btn.textContent = 'Copied!';
        btn.classList.add('copied');
        setTimeout(() => {
          btn.textContent = 'Copy';
          btn.classList.remove('copied');
        }, 2000);
      }
    });
  });
}

function getParentCode(btn) {
  const block = btn.closest('.code-block');
  const code  = block?.querySelector('code');
  return code?.textContent || '';
}

/* ============================
   Active Nav Link Tracking
   ============================ */
function initActiveNavTracking() {
  const sections  = document.querySelectorAll('.doc-section, .hero');
  const navLinks  = document.querySelectorAll('.nav-link[href^="#"]');

  const observer = new IntersectionObserver(
    entries => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          const id = entry.target.id;
          navLinks.forEach(link => {
            link.classList.toggle('active', link.getAttribute('href') === `#${id}`);
          });
        }
      });
    },
    { rootMargin: '-20% 0px -70% 0px' }
  );

  sections.forEach(s => observer.observe(s));
}

/* ============================
   Live Input Previews
   ============================ */
function initLiveInputPreviews() {
  ['users', 'grades', 'companies'].forEach(endpoint => {
    const input   = document.getElementById(`${endpoint}-count`);
    const preview = document.getElementById(`${endpoint}-url-preview`);

    if (input && preview) {
      input.addEventListener('input', () => {
        const count = parseInt(input.value) || 1;
        preview.textContent = `${BASE_URL}/api/random/${endpoint}/${count}`;
      });
    }
  });
}

/* ============================
   Live API Tester
   ============================ */
window.tryEndpoint = async function (endpoint) {
  const input    = document.getElementById(`${endpoint}-count`);
  const respBox  = document.getElementById(`${endpoint}-try-response`);
  const btn      = document.querySelector(`#try-${endpoint} .try-btn`);

  const count = Math.max(1, Math.min(20, parseInt(input?.value) || 1));
  const url   = `${BASE_URL}/api/random/${endpoint}/${count}`;

  respBox.textContent  = `⏳ Sending GET ${url}…`;
  respBox.className    = 'try-response loading';
  if (btn) btn.disabled = true;

  try {
    const response = await fetch(url, {
      method: 'GET',
      headers: { 'Accept': 'application/json' },
    });

    const text = await response.text();

    let pretty;
    try {
      pretty = JSON.stringify(JSON.parse(text), null, 2);
    } catch {
      pretty = text;
    }

    respBox.textContent = `// HTTP ${response.status} ${response.statusText}\n// ${url}\n\n${pretty}`;
    respBox.className   = response.ok ? 'try-response success' : 'try-response error';

  } catch (err) {
    respBox.textContent = `// Network error — the API may be sleeping (Render free tier spins down after inactivity).\n// Please wait ~30 seconds and try again.\n\nError: ${err.message}`;
    respBox.className   = 'try-response error';
  } finally {
    if (btn) btn.disabled = false;
  }
};

/* ============================
   API Status Check
   ============================ */
async function checkApiStatus() {
  const dot      = document.querySelector('.status-dot');
  const textEl   = document.getElementById('statusText');

  if (!dot || !textEl) return;

  try {
    const controller = new AbortController();
    const timeout    = setTimeout(() => controller.abort(), 8000);

    const res = await fetch(`${BASE_URL}/api/random/users/1`, {
      signal: controller.signal,
    });

    clearTimeout(timeout);

    if (res.ok) {
      dot.classList.add('online');
      textEl.textContent = 'API Online';
    } else {
      dot.classList.add('offline');
      textEl.textContent = `API Error ${res.status}`;
    }
  } catch {
    dot.classList.add('offline');
    textEl.textContent = 'API Offline / Starting…';
  }
}

/* ============================
   Smooth scroll polyfill helper
   ============================ */
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
  anchor.addEventListener('click', e => {
    const target = document.querySelector(anchor.getAttribute('href'));
    if (target) {
      e.preventDefault();
      const offset = 64; // topbar height + some breathing room
      const top    = target.getBoundingClientRect().top + window.scrollY - offset;
      window.scrollTo({ top, behavior: 'smooth' });
    }
  });
});

/* ============================
   Keyboard shortcut: / to scroll to examples
   ============================ */
document.addEventListener('keydown', e => {
  if (e.key === '/' && document.activeElement.tagName !== 'INPUT') {
    e.preventDefault();
    document.getElementById('examples')?.scrollIntoView({ behavior: 'smooth' });
  }
});
